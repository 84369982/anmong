package com.anmong.core.service.web;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.fastsql.util.FastSqlUtils;
import com.anmong.core.dao.FileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anmong.common.config.MyConfig;
import com.anmong.common.pagination.DataTable;
import com.anmong.common.util.FileUtil;

import com.anmong.core.dto.web.file.WebFileIndexDTO;
import com.anmong.core.entity.File;
import com.anmong.core.enums.FileEnum;
import org.springframework.util.StringUtils;


@Service
public class WebFileService {
	
/*	@Autowired
	private FileMapper fileMapper;*/

	@Autowired
	private FileDAO fileDAO;
	
	public void addBatch(List<File> fileList) {
		if(fileList != null && fileList.size() > 0) {
			fileDAO.insertBatch(fileList);
		}
	}
	
	public void updateBizIdByFileId(String bizId, String fileId) {
/*		File myFile = new File();
		myFile.setBizId(bizId);
		SqlBuilder sql = new SqlBuilder();
		sql.update(myFile);
		sql.where("id",SqlBuilder.equal(id));
		fileMapper.update(sql.build());*/
		if (!StringUtils.isEmpty(bizId) && !StringUtils.isEmpty(fileId)){
			fileDAO.updateSetWhere("biz_id = ?","id = ?",bizId,fileId);
		}

	}
	
	public void deleteBatch(List<File> fileList) {
		if(fileList.size() > 0) {
			List<String> idList = new ArrayList<>();
			for(File file : fileList) {
				idList.add(file.getId());
			}
		/*	fileMapper.delete(new SqlBuilder()
					.where("id", SqlBuilder.in(idList))
					.build());*/
			if (!idList.isEmpty()){
				fileDAO.deleteWhere("id IN " + FastSqlUtils.getInClause(idList));
				for(File deleteFile : fileList) {
					FileUtil.commonDelete(deleteFile.getPath(), deleteFile.getStoreType().intValue());
				}
			}
		}
		
		//删除存储的文件以节约资源
	
	}

	/**
	 * 单个删除文件
	 * @param fileId
	 */
	public void deleteById(String fileId){
		File file = fileDAO.selectOneById(fileId);
		if (file != null){
			fileDAO.deleteWhere("id = ? " ,fileId);
			FileUtil.commonDelete(file.getPath(), file.getStoreType().intValue());
		}

	}
	
	public File findOne(String id) {
		/*return fileMapper.findOne(new SqlBuilder()
				.where("id", SqlBuilder.equal(id))
				.build());*/
		return fileDAO.selectOneById(id);
	}



	public DataTable findAll(HttpServletRequest request,WebFileIndexDTO dto) {
		DataTable dt = DataTable.getInstance(request, null);
/*		PageHelper.startPage(dt.getStart()/dt.getLength() + 1, dt.getLength());
		SqlBuilder sql = new SqlBuilder();
		sql.where("files.create_at", SqlBuilder.equealOrGreaterThan(indexDTO.getStartTime()));
		sql.where("files.create_at", SqlBuilder.equealOrLessThan(indexDTO.getEndTime()));
		sql.where("files.state", SqlBuilder.equal(indexDTO.getState()));
		sql.where("files.biz_id", SqlBuilder.equal(indexDTO.getBizId()));
		sql.where("users.username", SqlBuilder.like(indexDTO.getCreateMan()));
		sql.where("files.module_code", SqlBuilder.equal(indexDTO.getModuleCode()));
		sql.where("files.store_type", SqlBuilder.equal(indexDTO.getStoreType()));
		sql.where("files.type", SqlBuilder.equal(indexDTO.getType()));
		if(CommonEnum.Is.否.code.equals(indexDTO.getHasBizId())){
			sql.where("files.biz_id", SqlBuilder.isNull());
		}
		if(CommonEnum.Is.是.code.equals(indexDTO.getHasBizId())){
			sql.where("files.biz_id", SqlBuilder.notNull());
		}
		sql.orderBy("files.create_at", SqlBuilder.DESC);*/
		dto.setPageNO(dt.getStart()/dt.getLength() + 1);
		dto.setPageSize(dt.getLength());
		ResultPage<File> page = fileDAO.findAllFile(dto);
		dt.setData(page.getContent());
		dt.setRecordsTotal(page.getTotalElements());
		dt.setRecordsFiltered(page.getTotalElements());
		return dt;
	}
	
	public void changeState(File entity,HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();  
		String domainUrl = requestUrl.delete(requestUrl.length() - request.getRequestURI().length(), requestUrl.length()).toString();
		if(FileEnum.FileType.图片.code.intValue() == entity.getType().intValue()) {
			entity.setUrl(domainUrl+MyConfig.getConfig("system.default.disabledImgPath"));	
		}
		else{
			entity.setUrl(domainUrl+MyConfig.getConfig("system.default.disabledImgPath"));
		}
			/*	fileMapper.update(new SqlBuilder()
    			  .update(entity)
    			  .where("id", SqlBuilder.equal(entity.getId()))
    			  .build());*/
			fileDAO.updateSelective(entity);
	}

	 

}
