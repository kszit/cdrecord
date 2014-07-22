

var activeElement;

function getBrowser(get_ver){
    var get_borwser=navigator.userAgent;
    var res='unknow';
    var ver='unknow';
    var is_ie=get_borwser.indexOf('MSIE');
    var is_firefox=get_borwser.indexOf('Firefox');
    var is_opera=get_borwser.indexOf('Opera');
    var is_chrome=get_borwser.indexOf('Chrome');
    var is_safari=get_borwser.indexOf('Safari');
    if(is_ie!==-1){
        res='ie';
    }
    else if(is_firefox!==-1){
        res='firefox'
    }
    else if(is_opera!==-1){
        res='opera'
    }
    else if(is_chrome!==-1){
        res='chrome'
    }
    else if(is_safari!==-1){
        res='safari'
    }
    if(get_ver===true){
        if(res=='ie'){
            ver=parseFloat(get_borwser.substring(is_ie+5,get_borwser.indexOf(";",is_ie)));
        }else{
            var explode=get_borwser.split("/");
            ver=parseFloat(explode[3]);
        }
        res=res+ver
    }
    return res;
}

function getRandom(countRandom){
    var res=Math.round(Math.random()*countRandom);
    return res;
}

var outBox=function(box_Id){
    var rand_num=getRandom(1000);
    var get_browser=getBrowser(true);
    var mask_case='mask_case_for_daluzai'+rand_num;
    var mask_obj=new mask(mask_case);
    var ww=$(document).width();
    var wh=$(document).height();
    var gw=$(window).width();
    var gh=$(window).height();
    var inputTextObj;
    this.openBox=function(box_show_style,mask_style,is_center,inputTextObj){
        this.inputTextObj = inputTextObj;
        var oldText = inputTextObj.value;
        $('#popDivText').val(oldText);
        
        
        $('#'+box_Id).fadeIn(600,function(){
            $(this).attr('className',box_show_style);
        })
        mask_obj.addMask(mask_style);
        if(is_center===true){
            var bw=(gw/2)-$('#'+box_Id).width()/2;
            var bh=(gh/2)-$('#'+box_Id).height()/2;
            $('#'+box_Id).css({
                'top':bh,
                'left':bw
            });
            if(get_browser==='ie6'){
                var is_position='absolute';
            }else{
                var is_position='fixed';
            }
        }
        $('#'+box_Id).css({
            'position':is_position,
            'z-index':1000
        });
        $('#'+mask_case).click(function(){
            quitBox_event();
        })
    }
    this.quitBox=function(){
        quitBox_event();
    }
    function quitBox_event(){
        $('#'+box_Id).fadeOut(600,function(){
            mask_obj.delMask();
            
            activeElement.value =  $('#popDivText').val();
        })
    }
}

var mask=function(mask_case){
    this.addMask=function(mask_style){
        $(document.body).append('<div id="'+mask_case+'"></div>');
        var ww=$(document).width();
        var wh=$(document).height();
        var gw=$(window).width();
        var gh=$(window).height();
        $('#'+mask_case).css({
            'width':'100%',
            'height':wh,
            'position':'absolute',
            'z-index':999,
            'top':0,
            'left':0
        });
        if(mask_style!==''&&mask_style!=='undefined'){
            $('#'+mask_case).addClass(mask_style);
        }
    }
    this.delMask=function(){
        $('#'+mask_case).fadeOut(600,function(){
            $(this).empty().remove();
        })
    }
}






