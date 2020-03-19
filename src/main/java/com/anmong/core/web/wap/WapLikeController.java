package com.anmong.core.web.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.wap.like.WapLikeCreateDTO;
import com.anmong.core.dto.wap.like.WapLikeIndexDTO;
import com.anmong.core.service.wap.WapLikeService;
import com.anmong.core.vo.wap.like.WapLikeIndexVO;
import com.anmong.core.vo.wap.like.WapLikeResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author songwenlong
 * 2018/3/12
 */
@RestController
@RequestMapping("wap/like")
public class WapLikeController {

    @Autowired
    private WapLikeService wapLikeService;

    @PostMapping("add")
    @ApiOperation(value = "添加或取消点赞",tags = "wap-点赞",response = WapLikeResultVO.class)
    public DosserReturnBody add(@Valid @RequestBody WapLikeCreateDTO dto){
        return new DosserReturnBodyBuilder().collectionItem(wapLikeService.add(dto)).build();
    }

    @GetMapping("find-like-info")
    @ApiOperation(value = "微博详情内点赞列表", tags = "wap-点赞",response = WapLikeIndexVO.class)
    public DosserReturnBody findAllForwardInfo(@Valid WapLikeIndexDTO dto){
        ResultPage<WapLikeIndexVO> resultPage = wapLikeService.findAllLikeInfo(dto);
        return new DosserReturnBodyBuilder().paginate(dto.getPageNO(),dto.getPageSize(),resultPage.getTotalElements())
                .collection(resultPage.getContent()).build();

    }
}
