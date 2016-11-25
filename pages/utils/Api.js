module.exports.startListNetWork = startListNetWork;
module.exports.startDetailNetWork = startDetailNetWork;

//获取专栏List集合
function startListNetWork(urlSuffix,callback){
    netWork(getList() + urlSuffix + "/posts?limit=20&offset=0",callback);
}

//获取专栏详情数据
function startDetailNetWork(slug,callback){
    netWork(getDetail() + slug,callback);
}

function netWork(url,callback){
      wx.showToast({
            title: '正在加载，请稍后...'
          })
      wx.request({
        url: getBaseUrl() + url,
        method: 'GET', 
        success: function(res){
          // console.log(res.code);
          callback(res.data);
          
        },
        fail: function() {
            console.log("网络请求失败");
            wx.showToast({
                title: '网络请求失败'
              })
            callback("error");
        },
        complete: function() {
          wx.hideToast();
          console.log("网络请求完成");
        }
      })
}

function getBaseUrl(){
  return "https://zhuanlan.zhihu.com/api/";
}

function getList(){
  return "columns/";
}

function getDetail(){
  return "posts/";
}