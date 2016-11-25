var app = getApp();
Page({
   //最美应用
  zuimei: function(weimei) {
    wx.navigateTo({
        url: '../list/list?suffix=zuimei',
     })
  }, 
  //小道消息
  WebNotes: function() {
     wx.navigateTo({
       url: '../list/list?suffix=WebNotes',
     })
  },
  //程序员的自我修养
  netjob: function() {
     wx.navigateTo({
       url: '../list/list?suffix=netjob',
     })
  }, 
  //知乎日报
  daily: function() {
     wx.navigateTo({
       url: '../list/list?suffix=daily',
     })
  }, 
  //MovieDaily
  MovieDaily: function() {
     wx.navigateTo({
       url: '../list/list?suffix=MovieDaily',
     })
  },
  data: {
    address:"github",
    motto: '知乎专栏',
    userInfo: {
      nickName:"@",
      avatarUrl:"https://avatars2.githubusercontent.com/u/16410317?v=3&s=460" 
   }
  } 
})
