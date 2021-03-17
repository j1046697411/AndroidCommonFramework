package org.jzl.router;

public class Postcard {

    private final String group;
    private final String path;
//    private Bundle params;

    private int flags;
    private int requestCode = -1;
    private String action;

    private boolean greenChannel = false;

    private RouteMeta routeMeta;
    private Object result;


    public Postcard(String group, String path) {
        this.group = group;
        this.path = path;
//        this.params = new Bundle();
    }

    public Postcard(String group, String path, Postcard postcard){
        this.group = group;
        this.path = path;
        this.flags = postcard.flags;
        this.requestCode = postcard.requestCode;
        this.action = postcard.action;
        this.greenChannel = postcard.greenChannel;
//        this.params = new Bundle(postcard.params);
    }

    public String getGroup() {
        return group;
    }

    public String getPath() {
        return path;
    }

//    public Bundle getExtras() {
//        return params;
//    }

    public int getFlags() {
        return flags;
    }

    public String getAction() {
        return action;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setGreenChannel(boolean greenChannel) {
        this.greenChannel = greenChannel;
    }


    public Postcard withRouteMeta(RouteMeta routeMeta){
        this.routeMeta = routeMeta;
        return this;
    }

    public Postcard withFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public Postcard withAction(String action){
        this.action = action;
        return this;
    }

    public Postcard withRequestCode(int requestCode){
        this.requestCode = requestCode;
        return this;
    }

    public Postcard addFlags(int flags) {
        this.flags |= flags;
        return this;
    }

    public Postcard with(String key, Object value) {
//        this.params.putString(key, serializationService.objectToJson(value));
        return this;
    }

//    public Postcard with(String key, String value){
//        this.params.putString(key, value);
//        return this;
//    }
//
//    public Postcard with(String key, boolean value){
//        this.params.putBoolean(key, value);
//        return this;
//    }
//
//    public Postcard with(String key, short value){
//        this.params.putShort(key, value);
//        return this;
//    }
//
//    public Postcard with(String key, int value){
//        this.params.putInt(key, value);
//        return this;
//    }
//
//    public Postcard with(String key, long value){
//        this.params.putLong(key, value);
//        return this;
//    }
//
//    public Postcard with(String key, float value){
//        this.params.putFloat(key, value);
//        return this;
//    }
//
//    public Postcard with(String key, double value){
//        this.params.putDouble(key, value);
//        return this;
//    }
//
//    public Postcard with(String key, Serializable value){
//        this.params.putSerializable(key, value);
//        return this;
//    }
//
//    public Postcard with(String key, Parcelable value){
//        this.params.putParcelable(key, value);
//        return this;
//    }

    public boolean isGreenChannel() {
        return greenChannel;
    }

    public RouteMeta getRouteMeta() {
        return routeMeta;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @SuppressWarnings("unchecked")
    public <T> T getResult(){
        return (T) result;
    }

}
