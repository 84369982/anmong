package com.anmong.core.web.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.wap.attention.WapAttentionCreateDTO;
import com.anmong.core.dto.wap.attention.WapAttentionIndexDTO;
import com.anmong.core.service.wap.WapAttentionService;
import com.anmong.core.vo.wap.attention.WapAttentionIndexVO;
import com.anmong.core.vo.wap.like.WapLikeIndexVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author songwenlong
 * 2018/3/13
 */
@RestController
@RequestMapping("wap/attention")
public class WapAttentionController {

    @Autowired
    private WapAttentionService wapAttentionService;

    @PostMapping("add")
    @ApiOperation(value = "添加关注", tags = "wap-关注")
    public DosserReturnBody add(@Valid @RequestBody WapAttentionCreateDTO dto){
        wapAttentionService.add(dto);
        return new DosserReturnBodyBuilder().statusOk().build();
    }

    @PostMapping("delete")
    @ApiOperation(value = "取消关注", tags = "wap-关注")
    public DosserReturnBody delete(@Valid @RequestBody WapAttentionCreateDTO dto){
        wapAttentionService.delete(dto);
        return new DosserReturnBodyBuilder().statusOk().build();
    }

    @GetMapping("find-all-attention")
    @ApiOperation(value = "获取关注列表", tags = "wap-关注",response = WapAttentionIndexVO.class)
    public DosserReturnBody findAllAttentionList(@Valid WapAttentionIndexDTO dto){
        ResultPage<WapAttentionIndexVO> resultPage = wapAttentionService.findAllAttentionList(dto);
        return new DosserReturnBodyBuilder().paginate(dto.getPageNO(),dto.getPageSize(),resultPage.getTotalElements())
                .collection(resultPage.getContent()).build();

    }

    @GetMapping("find-all-fans")
    @ApiOperation(value = "获取粉丝列表", tags = "wap-关注",response = WapAttentionIndexVO.class)
    public DosserReturnBody findAllFansList(@Valid WapAttentionIndexDTO dto){
        ResultPage<WapAttentionIndexVO> resultPage = wapAttentionService.findAllFansList(dto);
        return new DosserReturnBodyBuilder().paginate(dto.getPageNO(),dto.getPageSize(),resultPage.getTotalElements())
                .collection(resultPage.getContent()).build();

    }
}
