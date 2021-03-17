package org.jzl.router;

import android.content.Context;

public interface INavigator {

    Postcard createPostcard(String path);

    Postcard forward(String path, Postcard oldPostcard);

    Object navigation(Context context,INavigator navigator, Postcard postcard, INavigationCallback navigationCallback);

}
