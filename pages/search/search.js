var inPutValue ="";
Page({
  bindKeyInput: function(e) {
    inPutValue = e.detail.value;
  },
  startSearch: function() {
    if(inPutValue == ""){
          wx.showToast({
            title: '请输入专栏suffix'
          })
    }else{
         wx.navigateTo({
           url: '../list/list?suffix=' + inPutValue,
         })
    }
  }
});

