package com.kang.utils;

import android.content.Context;
import android.text.TextUtils;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;
import com.kang.R;
import com.kang.widget.MaterialDialog;

import java.util.List;

/**
 * @author ：kangjinling
 * 邮箱 ：401205099@qq.com
 * 功能描述 ：
 *
 */
public final class PermissionSetting {

    private final Context mContext;
    MaterialDialog mMaterialDialog;
    public PermissionSetting(Context context) {
        this.mContext = context;
    }

    public void showSetting(final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(mContext, permissions);
        String message = mContext.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        final SettingService settingService = AndPermission.permissionSetting(mContext);



        mMaterialDialog = new MaterialDialog(mContext);
        if (mMaterialDialog != null){
            mMaterialDialog.setTitle(R.string.title_dialog)
                    .setMessage(message)
                    .setPositiveButton("好的",v -> {
                        settingService.execute();
                        mMaterialDialog.dismiss();

                    })
                    .setNegativeButton("不行",v -> {
                        settingService.cancel();
                        mMaterialDialog.dismiss();
                    })
                    .setCanceledOnTouchOutside(false)
                    .show();
        }

    }
}