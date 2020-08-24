
function html2Escape(sHtml) {
    return sHtml.replace(/[<>&"]/g, function (c) { return { '<': '&lt;', '>': '&gt;', '&': '&amp;', '"': '&quot;' }[c]; });
}
$(function () {
    var x = S("roommap").attr("lng"), y = S("roommap").attr("lat"), apt = S("roommap").attr("address"), roomid = $("#roomid").val(),fei=$("#fei").val();
    jiaotongmap(roomid, x, y, apt, fei);//获取推荐乘车路线
});
/*
根据  经度纬度   房源的详细地址   截取到城市  再拼接字符串  循环获取到（起始地点-终点）的乘车方案
*/
function jiaotongmap(rooid,x, y, str1,fei) {
    if(fei !=0)
    {
        var map = new BMap.Map("allmaps");
        var localSearch = new BMap.LocalSearch(map);
        var citystrs = str1.split('-');
        var citystr = citystrs[0];
        var str = citystr + "站," + citystr + "西站," + citystr + "南站," + citystr + "东站," + citystr + "北站";
        var dibiaostr = str.split(',')
        for (var i = 0; i < dibiaostr.length; i++) {
            searchByStationName(localSearch, dibiaostr[i], rooid, x, y, fei, i, str1);
        }
    }
}
/*
根据  经度纬度   起始地点——终点  获取到乘车方案（选取3条）
*/
function huanchengmap(lng, lat, rooid, x, y, fei, i, str1,keyword) {
    var localSearch = new BMap.LocalSearch(map);
    var map = new BMap.Map("allmap");
    map.centerAndZoom(new BMap.Point(x, y), 10);
    map.enableScrollWheelZoom();
    var endPoint = new BMap.Point(x,y);
    var startPoint = new BMap.Point(lng,lat);
    var transit = new BMap.TransitRoute(map, {
        renderOptions: { map: map }, onSearchComplete: function (result) {
            if (transit.getStatus() == BMAP_STATUS_SUCCESS) {
                // 输出方案信息
                var s = [];
                var t = [];
                var outputtime = "";//获取时间
                var outputmi = "";//获取距离
                var huanchengnum = 0;//换乘次数
                if (result.getNumPlans() > 0) {
                    var plan = result.getPlan(0);
                    s.push("<span style=\"font-weight: bold;color: #ff8476;\">方案1</span> " + plan.getDescription(true).replace(/\'/gi,"\"") + "。");
                    t.push(plan.getDescription(false));
                    outputtime = plan.getDuration(true);
                    outputmi = plan.getDistance(true);
                    huanchengnum = plan.getNumLines(true);
                }
                var temp =s.join("<br/>");
                var tte = t.join("<br/>");
                if (s != null && s != "") {
                    var title = "";//经过的路线
                    var buxingmi = 0;//整个过程中步行的距离
                    $("#r-result").html(temp + "<br/>");
                    $("#r-result .navtrans-bus-desc").each(function (index) {
                        var Ctshtml = $(".navtrans-bus-desc").eq(index).html();
                        var reg = new RegExp("公里", "g"); //创建正则RegExp对象   
                        var Cts = Ctshtml.replace(reg, "氹");
                        if (Cts.indexOf('米') > 0) {
                            var paddleft = Cts.replace(/[^0-9]/ig, "");
                            buxingmi += parseInt(paddleft);
                        }
                        if (Cts.indexOf('氹') > 0) {
                            var paddleft = Cts.replace(/[^0-9]/ig, "");
                            buxingmi += parseInt(paddleft) * 1000;
                        }
                    });
                    zongstr = tte + "|" + outputtime + "|" + outputmi + "|" + buxingmi + "|" + huanchengnum;
                    add(lng, lat, keyword, str1, x, y, zongstr, html2Escape(temp), rooid, fei, i);
                }
            }
        }
    });
    transit.search(startPoint, endPoint);
}
/*
starAddress --起点, endAddress --终点, x --经度, y --纬度, content --百度抓取的内容, shtml -- 百度抓取html的内容, rooid --房源id,fei --根据这个判断是添加还是修改,i  --根据这个判断需不需要删除之前存在的该房源的数据
*/
function add(lng, lat, starAddress, endAddress, x, y, content, shtml, rooid, fei, i)
{
    var isi=true;
    if(i>0)
    {
        isi=false;
    }
    $.post("/Home/AddBaiDuApi", {
        Content: content,
        StarAddress: starAddress,
        EndAddress: endAddress,
        Lng: lng,
        Lat: lat,
        roomLng: x,
        roomLat: y,
        Htmlstr: shtml,
        RoomId:rooid,
        Fei:fei,
        Isdelete:isi
    }, function (data) {
    });
}
function searchByStationName(localSearch, keyword, rooid, x, y, fei, i, str1) {
    localSearch.setSearchCompleteCallback(function (searchResult) {
        var poi = searchResult.getPoi(0);
        if (typeof poi!="undefined") {
            var title = poi.title;
            var lng = poi.point.lng;
            var lat = poi.point.lat;
            huanchengmap(lng, lat, rooid, x, y, fei, i, str1, title);
        }
    });
    localSearch.search(keyword);
}