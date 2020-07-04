package com.cskaoyan.mall.bean.vo;

import com.cskaoyan.mall.bean.SystemPermissions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemPermissionsVo {
    List<Integer> assignedPermissions;
    List<SystemPermissions> systemPermissions;
}
