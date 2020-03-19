package com.anmong.core.web.wap;

import com.anmong.common.fastsql.dto.ResultPage;
import com.anmong.common.message.DosserReturnBody;
import com.anmong.common.message.DosserReturnBodyBuilder;
import com.anmong.core.dto.wap.articleforward.WapForwardBlogIndexDTO;
import com.anmong.core.dto.wap.articleforward.WapForwardCreateDTO;
import com.anmong.core.service.wap.WapArticleForwardService;
import com.anmong.core.vo.wap.article.WapForwardBlogVO;
import com.anmong.core.vo.wap.article.WapForwardIndexVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author songwenlong
 * 2018/3/7
 */
@RestController
@RequestMapping("wap/article-forward")
public class WapArticleForwardController {

    @Autowired
    private WapArticleForwardService wapArticleForwardService;

    @GetMapping("find-forward-info")
    @ApiOperation(value = "微博详情内转发列表", tags = "wap-微博",response = WapForwardBlogVO.class)
    public DosserReturnBody findAllForwardInfo(@Valid WapForwardBlogIndexDTO dto){
        ResultPage<WapForwardIndexVO> resultPage = wapArticleForwardService.findAllForwardInfo(dto);
        return new DosserReturnBodyBuilder().paginate(dto.getPageNO(),dto.getPageSize(),resultPage.getTotalElements())
                .collection(resultPage.getContent()).build();

    }

    @PostMapping("add")
    @ApiOperation(value = "转载文章", tags = "wap-微博")
    public DosserReturnBody findAllForwardInfo(@RequestBody @Valid WapForwardCreateDTO dto){
        wapArticleForwardService.add(dto);
        return new DosserReturnBodyBuilder().statusOk().build();

    }

}
