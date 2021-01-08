$(document).ready(function() {
    var url = window.location.href;
    console.info(">>>Cola<<<");
   if(url.indexOf("iptv")!=-1){
      $('.headerNfooter').remove();
      $('.ui-footer').remove();
         
      if (url.indexOf("?act=play") != -1) {
          $("#myEpg [target=\"_blank\"]").parent().parent().remove();
      }
 
      $("[target=\"_blank\"]").remove();

      $("a[href*=\"down.php\"]").remove();
      $("a[href*=\"tid=ipv6\"]").parent().parent().remove();
      $("a[href*=\"tid=wintv123\"]").parent().parent().remove();

      $(".ui-listview").prev().hide();
      $("form").show();
      $(".ui-content").prev().hide();
      $(".ui-content").css("padding","15px 0px 0px 0px");
   }

   $("video").on("loadstart", function() {
        var v = $("video");
        $(this).outerHeight(210);
        console.info(v);
        try{
            v.css({
               visibility:"hidden"
            });
             var vv = getObjXy(v.get(0));
            console.info("-------BoundingClientRect-------");
            console.info("top:" + vv.top);
            console.info("left:" + vv.left);
            console.info("right:" + vv.right);
            console.info("bottom:" + vv.bottom);
            console.info("width:" + vv.width);
            console.info("height:" + vv.height);


          /*  console.info("-------offset-------");
            console.info("top:"+v[0].offsetTop);
            console.info("left:"+v[0].offsetLeft);
            console.info("width:"+v.parent()[0].offsetWidth);
            console.info("height:"+v.parent()[0].offsetHeight);
            console.info("src:"+v[0].src);*/

            //android.addVideoPlayerView(v[0].offsetWidth, v[0].offsetHeight, v[0].offsetTop, v[0].offsetLeft, v[0].src,v[0].baseURI);
            android.addVideoPlayerView(vv.width, vv.height, vv.top, vv.left, v.get(0).src,v.get(0).baseURI);

            $("video").trigger("pause");
            $("video").addClass('pause');
            $("video").removeClass('play');
        }catch(e){
            console.info(e);
        }
    });
});

function getObjXy(obj){
        var xy = obj.getBoundingClientRect();
        var top = xy.top - document.documentElement.clientTop + document.documentElement.scrollTop,
　　　　　//document.documentElement.clientTop 在IE67中始终为2，其他高级点的浏览器为0
            bottom = xy.bottom,
            left = xy.left - document.documentElement.clientLeft + document.documentElement.scrollLeft,
　　　　　　　//document.documentElement.clientLeft 在IE67中始终为2，其他高级点的浏览器为0
            right = xy.right,
            width = xy.width || right - left, //IE67不存在width 使用right - left获得
            height = xy.height || bottom - top;

        return {
            top:top,
            right:right,
            bottom:bottom,
            left:left,
            width:width,
            height:height
        }
}

function clicked(url){
    var base = "https://player.ggiptv.com/iptv.php";
    console.info(base+url);
    window.location.href=base+url;
}

function getCookie(id){
    console.info(id);
    return id;
}

function doCreateAd(){
    console.info("doCreateAd");
}