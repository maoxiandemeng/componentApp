package com.jing.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by jon on 2017/3/2
 */

public class ActivityRouter {

    public static void openActivity(Context context, String uri){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

}
