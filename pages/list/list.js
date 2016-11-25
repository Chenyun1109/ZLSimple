var network = require('../utils/Api.js')
var that;

Page({
  data: {
  },
  onLoad:function(option){
     that = this
     network.startListNetWork(option.suffix,netWorkCallback);
  }
});

function netWorkCallback(data){
    // that.data.item = data;
    if(data[0].title == undefined){
           wx.showModal({
                title: '错误',
                content: '加载失败，请查看网络或者suffix',
                success: function(res) {
                    if (res.confirm) {
                        wx.redirectTo({
                            url: '../search/search',
                        })
                    }
                }
            })
    }else{
            that.setData({
                item:data,
            })
    }
}

