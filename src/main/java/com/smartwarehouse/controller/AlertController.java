package com.smartwarehouse.controller;

import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.AlertRecord;
import com.smartwarehouse.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预警管理 Controller
 */
@RestController
@RequestMapping("/api/alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping("/scan")
    public Result<List<AlertRecord>> scan() {
        return Result.success(alertService.scanLowStock());
    }

    @GetMapping("/unhandled")
    public Result<List<AlertRecord>> unhandled() {
        return Result.success(alertService.listUnhandled());
    }
}
