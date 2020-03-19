package com.anmong.core.dao;

import com.anmong.common.fastsql.dao.BaseDAO;
import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.util.DateUtil;
import com.anmong.core.dto.web.file.WebFileIndexDTO;
import com.anmong.core.entity.File;
import com.anmong.core.enums.CommonEnum;
import com.anmong.core.vo.wap.file.FileIndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class FileDAO extends BaseDAO<File, String>{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertBatch(List<File> entityList){
        StringBuilder sql = new StringBuilder("INSERT INTO files (id,module_name,module_code,name,old_file_name,new_file_name,url,path,store_type,suffix,size,type,state,create_at,create_man) values ");
        for (File entity : entityList){
            sql.append("('"+entity.getId()+"','"+entity.getModuleName()+"','"+entity.getModuleCode()+"','"+entity.getName())
                    .append("','"+entity.getOldFileName()+"','"+entity.getNewFileName()+"','"+entity.getUrl()+"','"+entity.getPath())
                    .append("','"+entity.getStoreType()+"','"+entity.getSuffix()+"','"+entity.getSize()+"','"+entity.getType())
                    .append("','"+entity.getState()+"','"+ DateUtil.getYYYYmmDDhhMMss(entity.getCreateAt())+"','"+entity.getCreateMan()+"'),");
        }
        sql.replace(sql.lastIndexOf(","),sql.lastIndexOf(",")+1,";");
        namedParameterJdbcTemplate.update(sql.toString(), EmptySqlParameterSource.INSTANCE);
    }

    public ResultPage<File> findAllFile(WebFileIndexDTO dto) {
        return getSQL()
                .useSql("SELECT files.id,files.module_name,files.name,files.old_file_name,files.new_file_name,files.url,files.store_type,files.suffix,files.state,files.create_at,files.size,files.type,files.biz_id,files.module_code,users.username AS create_man ")
                .FROM("files")
                .LEFT_JOIN_ON("users","files.create_man = users.id")
                .WHERE()
                .ifPresentAND("files.create_at >= ",dto.getStartTime())
                .ifPresentAND("files.create_at >= ",dto.getEndTime())
                .ifPresentAND("files.state = ",dto.getState())
                .ifPresentAND("files.biz_id = ",dto.getBizId())
                .ifPresentAND("users.username LIKE ","%"+dto.getCreateMan()+"%")
                .ifPresentAND("files.module_code = ",dto.getModuleCode())
                .ifPresentAND("files.store_type = ",dto.getStoreType())
                .ifPresentAND("files.type = ",dto.getType())
                .ifTrueAND("files.biz_id IS NULL",CommonEnum.Is.否.code.equals(dto.getHasBizId()))
                .ifTrueAND("files.biz_id IS NOT NULL",CommonEnum.Is.是.code.equals(dto.getHasBizId()))
                .ORDER_BY("files.create_at").DESC()
                .queryPage(dto.getPageNO(),dto.getPageSize(),File.class);
    }

    public List<FileIndexVO> findFileListByBizId(String bizId){
        return getSQL()
                .SELECT("type,url")
                .FROM("files")
                .WHERE("biz_id").eqByType(bizId)
                .ORDER_BY("create_at").ASC()
                .queryList(FileIndexVO.class);
    }
}
