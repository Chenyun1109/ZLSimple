var network = require('../utils/Api.js')
var that;

Page({
  data: {
  },
  onLoad:function(option){
     that = this
     network.startDetailNetWork(option.slug,netWorkCallback);
  }
});

function netWorkCallback(data){
    that.setData({
         detail:data,
    })
}
