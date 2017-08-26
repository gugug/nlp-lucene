require.config({
    paths : {
        echarts : '../public/javascript/echarts-2.2.7/build/dist'
    }
});

require([ "echarts", "echarts/chart/force"], function(ec) {
var myChart = ec.init(document.getElementById('Echart'), 'macarons');
var option = {
    tooltip : {
        show : false
    },
    calculable : false,
    animation: false,
    series : [ {
            type : 'force',
            name : "Force tree",
            draggable:false,
            // useWorker:true,
            steps:1,
            gravity:0.6,
            // roam:'move',
            itemStyle : {
                normal : {
                    label : {show : true,
				         textStyle:{
							 fontWeight:900 
                        }
					},
                    nodeStyle : {
                        
                        borderColor : 'rgba(255,215,0,0)',
                        borderWidth : 0
                    },
                    linkStyle: {
                        type: 'curve',
                        color:'#2aaa90',
                        width:2
                    }
                    
                },
                emphasis:{
                    label : {show : true,
                             textStyle:{
                                 fontWeight:900 }
                             },
                    nodeStyle : {
                        color:'#f1ab66',
                        borderColor : 'rgba(255,255,255,1)',
                        borderWidth : 3
                    }
                }
            },
            categories : [ 
                {
                    name : '0',
                    symbolSize:40,
                    itemStyle:{
                        normal:{
                            color:'#24b1b6',
                            borderColor:'#18838b',
                            borderWidth:6,
                            label : {
                                show : true,
                                textStyle:{
                                    fontSize:15
                                }
                            }
                    
                        },
                        emphasis:{
                            borderWidth:6,
                            borderColor:'#fff',
                            label : {
                                show : true,
                                textStyle:{
                                    fontSize:15,
                                    fontWeight:700
                                }
                            }
                        }
                    }

                }, 
                {
                    name : '1',
                    symbolSize:25,
                    itemStyle:{
                        normal:{
                            color:'#2aaa90',
                            label : {
                                show : true,
                                textStyle:{
                                    fontSize:8
                                }
                            } 
                        }
                        

                    }
                }, 
                {
                    name : '2',
                    symbolSize:30,
                    itemStyle:{
                        normal:{
                            color:'#24b6bc',

                            label : {
                                show : true,
                                textStyle:{
                                    fontSize:13
                                }
                            } 
                            
                        },
                        emphasis:{
                            borderWidth:4,
                            borderColor:'#fff',
                            label : {
                                show : true,
                                textStyle:{
                                    fontSize:13,
                                    fontWeight:700
                                }
                            }
                        }
                    }
                }
            ],
            nodes : [
                {id:0,category:0,name:'film',label:'但丁密码',ignore:false,flag:true,initial:[500,320],fixX:true,fixY:true,card:'film'},
                {id:1,category:1,name:'theme',label:'主题',ignore:true,flag:true,initial:[620,260],fixX:true,fixY:true},
                {id:2,category:1,name:'content',label:'内容',ignore:true,flag:true,initial:[620,390],fixX:true,fixY:true},
                {id:3,category:1,name:'maker',label:'制作',ignore:true,flag:true,initial:[375,265],fixX:true,fixY:true},
                {id:4,category:1,name:'role',label:'角色',ignore:true,flag:true,initial:[375,394],fixX:true,fixY:true},
                {id:5,category:2,name:'style',label:'风格',ignore:true,flag:true,card:'style',initial:[680,130],fixX:true,fixY:true},
                {id:6,category:2,name:'background',label:'题材背景',ignore:true,flag:true,card:'background',initial:[780,210],fixX:true,fixY:true},
                {id:7,category:2,name:'awards',label:'获奖情况',ignore:true,flag:true,card:'awards',initial:[770,390],fixX:true,fixY:true},
                {id:8,category:2,name:'plot',label:'剧情解析',ignore:true,flag:true,card:'plot',initial:[740,455],fixX:true,fixY:true},
                {id:9,category:2,name:'music',label:'音乐原声',ignore:true,flag:true,card:'music',initial:[690,505],fixX:true,fixY:true},
                {id:10,category:2,name:'comment',label:'影片评价',ignore:true,flag:true,card:'comment',initial:[620,530],fixX:true,fixY:true},
                {id:11,category:2,name:'company',label:'发行公司',ignore:true,flag:true,card:'company',initial:[370,140],fixX:true,fixY:true},
                {id:12,category:1,name:'director',label:'导演',ignore:true,flag:true,initial:[300,180],fixX:true,fixY:true},
                {id:13,category:1,name:'scriptwriter',label:'编剧',ignore:true,flag:true,initial:[260,220],fixX:true,fixY:true},
                {id:14,category:1,name:'0',label:'罗伯特·兰登',ignore:true,flag:true,card:'person'},
                {id:15,category:1,name:'1',label:'西恩娜·布鲁克斯',ignore:true,flag:true,card:'person'},
                {id:16,category:1,name:'2',label:'哈利·希姆斯',ignore:true,flag:true,card:'person'},
                {id:17,category:1,name:'3',label:'克里斯多夫·布鲁多',ignore:true,flag:true,card:'person'},
                {id:18,category:2,name:'4',label:'朗·霍华德',ignore:true,flag:true,card:'person'},
                {id:19,category:2,name:'5',label:'大卫·凯普',ignore:true,flag:true,card:'person'},
                {id:20,category:2,name:'6',label:'汤姆·汉克斯',ignore:true,flag:true,card:'person'},
                {id:21,category:2,name:'7',label:'菲丽希缇·琼斯',ignore:true,flag:true,card:'person'},
                {id:22,category:2,name:'8',label:'伊尔凡·可汗',ignore:true,flag:true,card:'person'},
                {id:23,category:2,name:'9',label:'奥马·希',ignore:true,flag:true,card:'person'}
            ],

            links : [ 
                {source : 1,target : 0}, 
                {source : 2,target : 0}, 
                {source : 3,target : 0},
                {source : 4,target : 0}, 
                {source : 5,target : 1}, 
                {source : 6,target : 1}, 
                {source : 7,target : 2}, 
                {source : 8,target : 2}, 
                {source : 9,target : 2}, 
                {source : 10,target : 2}, 
                {source : 11,target : 3}, 
                {source : 12,target : 3},
                {source : 13,target : 3},
                {source : 14,target : 4},
                {source : 15,target : 4},
                {source : 16,target : 4},
                {source : 17,target : 4},
                {source : 18,target : 12},
                {source : 19,target : 13},
                {source : 20,target : 14},
                {source : 21,target : 15},
                {source : 22,target : 16},
                {source : 23,target : 17}

            ],

            // info:[
            //     {id : 0, name:'谢霆锋'}
            // ]
        }]
    };
    myChart.setOption(option);

    var ecConfig = require('echarts/config');
    function showNodes(param) {
        var data = param.data;
        if(data.flag == false){
            return false;
        }
        var option = myChart.getOption();
        var nodesOption = option.series[0].nodes;
        var linksOption = option.series[0].links;
        //用于存放所选节点的子节点
        var linksNodes = [];
        //如果flag值为false即为不可点击
        var categoryLength = option.series[0].categories.length;
        //如果所选节点category值最小，即没有子节点，则返回，不执行下面操作
        if (data.category == (categoryLength - 1)) {
            return false;
        }

        //在控制台中打印出相关信息
        // console.log('option:'+JSON.stringify(option));
        // console.log('data:'+JSON.stringify(data));
        
        if (data != null && data != undefined) {
            //如果data值中存在id属性，即点击的是node节点
            if ('id' in data) {
                //将flag值修改为false
                data.flag = false;
                //遍历linksOption,找出所有目标为当前所选节点的结点
                //并将下一级结点的id值存放在linksNodes数组中
                for(var i = 0; i < linksOption.length ; i++){
                    if(data.id == linksOption[i].target){
                        linksNodes.push(linksOption[i].source)
                    }
                }
                //遍历nodesOption数组，将对应的结点的ignore属性设置为false，即展示出来
                for(var j = 0; j < linksNodes.length ; j++){
                    nodesOption[linksNodes[j]].ignore = false;
                }
                //重新配置
                myChart.setOption(option);
            }
        }
    }
    function showBox(param) {
        // var infoBox = document.getElementById('infoBox')
        // infoBox.style.display = 'block'; 
        var data = param.data;
        if('card' in data){
            var cards = $('.card');

            cards.hide();

            var card_current = $('#'+data.card+'_card');
            card_current[0].style.marginRight = '0px';
            card_current.fadeIn();

            card_current.animate({marginRight:"100px"},160);
        }   
    }
    myChart.on(ecConfig.EVENT.HOVER,showNodes);
    myChart.on(ecConfig.EVENT.CLICK,showBox);
});