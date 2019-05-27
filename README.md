# BwPermissions

针对 targetSDKVersion 大于或等于23 的权限申请库。该库主要参考google的 [EasyPermissions 库][1]。

## 配置
在 module 层级的 build.gradle 引入：
```
dependencies {
    implementation 'com.benwang:permissions:1.0'
}
```

## 使用
### 基本使用
在 Activity (or Fragment) 重写 onRequestPermissionsResult 方法
```

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    PermissionsCore.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
}
```

### 权限请求

 - PermissionsCore#hasPermissions(...) 通过该方法判断是否已经具有某项/某些权限。可以输入任意数量的权限作为参数。
 - PermissionsCore#requestPermissions(...) 请求系统权限，在权限拒绝后第二次申请会弹出相应的权限申请说明。
 - PermissionsCore#requestDirectPermissions(...) 请求系统权限，直接请求系统权限，请求结果返回到回调。

### 请求回调结果处理
在Activity (or Fragment)实现 BwPermissions.PermissionCallbacks接口或者为了分离处理逻辑可以写一个独立的类实现BwPermissions.PermissionCallbacks：
```
public class MainActivity extends Activity implements BwPermissions.PermissionCallbacks {

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //权限被授予的回调

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //进行权限的处理

    }
}
```

在Activity (or Fragment)实现 BwPermissions.RationaleCallbacks接口：
```
public class MainActivity extends Activity implements BwPermissions.RationaleCallbacks {

    @Override
    public void onRationaleAccepted(int requestCode) {
        //确认再次进行权限申请
        Log.e(TAG, "确定 ~~~");
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        //需要某些权限的理由
        Log.e(TAG, "取消 ~~~");
    }
}
```
**具体使用可以参考demo。**


  [1]: https://github.com/googlesamples/easypermissions