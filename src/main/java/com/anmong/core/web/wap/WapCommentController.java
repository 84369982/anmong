package com.anmong.core.web.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.wap.comment.WapCommentCreateDTO;
import com.anmong.core.dto.wap.comment.WapCommentIndexDTO;
import com.anmong.core.service.wap.WapCommentService;
import com.anmong.core.vo.wap.comment.WapCommentIndexVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author songwenlong
 * 2018/3/8
 */
@RestController
@RequestMapping("wap/comment")
public class WapCommentController {

    @Autowired
    private WapCommentService wapCommentService;

    @GetMapping("find-all")
    @ApiOperation(value = "查询评论列表", tags = "wap-评论")
    public DosserReturnBody findArticleCommentList(@Valid WapCommentIndexDTO dto){
        ResultPage<WapCommentIndexVO> resultPage = wapCommentService.findArticleCommentList(dto);
        return new DosserReturnBodyBuilder().paginate(dto.getPageNO(),dto.getPageSize(),resultPage.getTotalElements())
                .collection(resultPage.getContent()).build();
    }

    @PostMapping("add")
    @ApiOperation(value = "添加评论",tags = "wap-评论")
    public DosserReturnBody add(@Valid @RequestBody WapCommentCreateDTO dto){
        wapCommentService.add(dto);
        return new DosserReturnBodyBuilder().statusOk().build();
    }
}
