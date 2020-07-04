package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 16:16
 */
@RestController
@RequestMapping("wx/collect")
public class WxCollectController {

    @Autowired
    CollectService collectService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer size, Byte type) {
        Map<String, Object> map = collectService.queryCollects(page, size, type);
        return BaseRespVo.ok(map);
    }

    @RequestMapping("addordelete")
    public BaseRespVo addordelete(@RequestBody Map<String, Object> map) {
        Integer type = (Integer) map.get("type");
        Integer valueId = (Integer) map.get("valueId");
        collectService.updateCollect(type, valueId);
        HashMap<String, String> result = new HashMap<>();
        if (collectService.queruCollectByValueId(valueId) == null) result.put("type", "add");
        else result.put("type", "delete");
        return BaseRespVo.ok(result);
    }
}
