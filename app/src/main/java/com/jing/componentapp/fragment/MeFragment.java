package com.jing.componentapp.fragment;

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.jing.componentapp.R;
import com.jing.componentapp.activity.DownLoadActivity;
import com.jing.componentapp.activity.TabActivity;
import com.jing.componentapp.activity.VideoPlayActivity;
import com.jing.componentapp.base.BaseLazyFragment;
import com.jing.componentapp.service.LocalScreenService;
import com.jing.componentapp.widget.ReloadImageView;
import com.jing.library.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liujing on 2017/10/24.
 */

public class MeFragment extends BaseLazyFragment {
    private static final String TAG = "MeFragment";

    @BindView(R.id.re_iv)
    ReloadImageView reIv;

    private String str = "{\"code\":\"0\",\"message\":\"获取成功\",\"data\":{\"id\":174,\"name\":\"sadsadas.zml\",\"grade\":\"一年级\",\"gradeId\":1,\"subject\":\"语文\",\"subjectId\":1,\"edition\":\"沪教版\",\"editionId\":90,\"courseSystemFirstId\":16569,\"courseSystemFirstName\":\"测试1-1\",\"courseSystemSecondId\":16609,\"courseSystemSecondName\":\"测试2-1\",\"courseSystemThirdId\":16610,\"courseSystemThirdName\":\"测试3-1\",\"courseSystemFourthId\":16611,\"courseSystemFourthName\":\"测试4-1\",\"difficulty\":1,\"difficultyStr\":\"一星\",\"scene\":\"2\",\"sceneStr\":\"同步\",\"content\":\"[{\\\"id\\\":5337510,\\\"name\\\":\\\"知识图谱\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337511,\\\"type\\\":\\\"page\\\"}]},{\\\"id\\\":5337512,\\\"name\\\":\\\"错题回顾\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337513,\\\"type\\\":\\\"page\\\"}]},{\\\"id\\\":5337514,\\\"name\\\":\\\"课后作业\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337515,\\\"type\\\":\\\"page\\\"}]},{\\\"id\\\":5339801,\\\"type\\\":\\\"slide\\\",\\\"slideId\\\":1198,\\\"name\\\":\\\"asdasda\\\",\\\"children\\\":[{\\\"id\\\":5337516,\\\"name\\\":\\\"知识精讲\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337517,\\\"type\\\":\\\"page\\\"}]},{\\\"id\\\":5337518,\\\"name\\\":\\\"三点剖析\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337519,\\\"name\\\":\\\"重点\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337520,\\\"type\\\":\\\"page\\\"}]},{\\\"id\\\":5337521,\\\"name\\\":\\\"难点\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337522,\\\"type\\\":\\\"page\\\"}]},{\\\"id\\\":5337523,\\\"name\\\":\\\"易错点\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337524,\\\"type\\\":\\\"page\\\"}]}]},{\\\"id\\\":5337525,\\\"name\\\":\\\"题模精讲\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337526,\\\"type\\\":\\\"page\\\"}]},{\\\"id\\\":5337527,\\\"name\\\":\\\"随堂练习\\\",\\\"type\\\":\\\"dir\\\",\\\"children\\\":[{\\\"id\\\":5337528,\\\"type\\\":\\\"page\\\"}]}]}]\"}}";
    //    private String str = "{\"id\":5337510,\"name\":\"知识图谱\",\"type\":\"dir\",\"children\":[{\"id\":5337511,\"type\":\"page\"},{\"id\":5337512,\"type\":\"page\"}]}";
//    private String str = "\"[{\"id\":5337510,\"name\":\"知识图谱\",\"type\":\"dir\",\"children\":[{\"id\":5337511,\"type\":\"page\"}]},{\"id\":5337512,\"name\":\"错题回顾\",\"type\":\"dir\",\"children\":[{\"id\":5337513,\"type\":\"page\"}]},{\"id\":5337514,\"name\":\"课后作业\",\"type\":\"dir\",\"children\":[{\"id\":5337515,\"type\":\"page\"}]},{\"id\":5339801,\"type\":\"slide\",\"slideId\":1198,\"name\":\"asdasda\",\"children\":[{\"id\":5337516,\"name\":\"知识精讲\",\"type\":\"dir\",\"children\":[{\"id\":5337517,\"type\":\"page\"}]},{\"id\":5337518,\"name\":\"三点剖析\",\"type\":\"dir\",\"children\":[{\"id\":5337519,\"name\":\"重点\",\"type\":\"dir\",\"children\":[{\"id\":5337520,\"type\":\"page\"}]},{\"id\":5337521,\"name\":\"难点\",\"type\":\"dir\",\"children\":[{\"id\":5337522,\"type\":\"page\"}]},{\"id\":5337523,\"name\":\"易错点\",\"type\":\"dir\",\"children\":[{\"id\":5337524,\"type\":\"page\"}]}]},{\"id\":5337525,\"name\":\"题模精讲\",\"type\":\"dir\",\"children\":[{\"id\":5337526,\"type\":\"page\"}]},{\"id\":5337527,\"name\":\"随堂练习\",\"type\":\"dir\",\"children\":[{\"id\":5337528,\"type\":\"page\"}]}]}]\"";
    private int count = 0;
    private ArrayList<String> jsonBeans = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init() {
        activity.startService(new Intent(activity, LocalScreenService.class));
    }

    @Override
    protected void onInVisible() {

    }

    @Override
    protected void lazyData() {
        reIv.setImageUrl("http://7xi8d6.com1.z0.glb.clouddn.com/20180208080314_FhzuAJ_Screenshot.jpeg");

        try {
            JSONObject object = new JSONObject(str);
            JSONObject data = object.getJSONObject("data");
            String content = data.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        TestBean bean = gson.fromJson(str, TestBean.class);
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(str, HashMap.class);
        for (String key : map.keySet()) {
            Object s = map.get(key);
            if ("data".equals(key)) {
                Map<String, Object> date = (Map<String, Object>) map.get(key);
                String content = (String) date.get("content");
                ArrayList<LinkedTreeMap<String, Object>> treeMaps = gson.fromJson(content, ArrayList.class);
                LogUtil.i(TAG, "content: " + content);
                intearMap(treeMaps);
//                for (HashMap<String, Object> hashMap :content){
//                    boolean children = hashMap.containsKey("children");
//                    if (children) {
//                        ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) hashMap.get("children");
//                        for (HashMap<String, Object> value :list) {
//                            boolean type = value.containsKey("type");
//                            if (type) {
//                                if ("page".equals(value.get("type"))) {
//
//                                }
//                            }
//                        }
//                    }
//                }
            }
            LogUtil.i(TAG, key + " s: " + s);
        }

        String json = new Gson().toJson(jsonBeans);
        LogUtil.i(TAG, "json: "+json);

        ArrayList<String> list = new Gson().fromJson(json, ArrayList.class);
        for (String s :list) {
            JsonBean jsonBean = new Gson().fromJson(s, JsonBean.class);
            LogUtil.i(TAG, "s" + s);
            LogUtil.i(TAG, "bean " + jsonBean.getId());
        }

    }

    private void intearMap(ArrayList<LinkedTreeMap<String, Object>> content) {
        for (LinkedTreeMap<String, Object> hashMap : content) {
            boolean children = hashMap.containsKey("children");
            if (children) {
                ArrayList<LinkedTreeMap<String, Object>> list = (ArrayList<LinkedTreeMap<String, Object>>) hashMap.get("children");
                intearMap(list);
            } else {
                boolean type = hashMap.containsKey("type");
                if (type) {
                    if ("page".equals(hashMap.get("type"))) {
                        count++;
                        Double id = (Double) hashMap.get("id");
                        LogUtil.i(TAG, "id: " + id + "page: " + count + "hashMap： " +paramsToJsonString(hashMap));
                        jsonBeans.add(paramsToJsonString(hashMap));
                    }
                }
            }
        }
    }
    public String paramsToJsonString(Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            JSONObject object = new JSONObject();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    try {
                        object.put(entry.getKey(), entry.getValue());
                        return object.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return "";
                    }
                }

        }
        return "";
    }


    @OnClick({R.id.btn_download, R.id.btn_look, R.id.video_play, R.id.btn_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                openActivity(DownLoadActivity.class);
                break;
            case R.id.btn_look:
                break;
            case R.id.video_play:
                openActivity(VideoPlayActivity.class);
                break;
            case R.id.btn_tab:
                openActivity(TabActivity.class);
                break;
        }
    }

    public class JsonBean {
        private int id;
        private String type;

        public JsonBean(int id, String type) {
            this.id = id;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class TestBean {
        private String code;
        private String message;
        private DataBean data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        class DataBean {
            private int id;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }

}
