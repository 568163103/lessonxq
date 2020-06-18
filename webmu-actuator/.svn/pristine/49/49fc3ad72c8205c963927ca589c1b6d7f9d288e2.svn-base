$(function(){
	var userid = $.cookie("userid")

	$.ajax({
        url: url + "/ims/getBaseInfo.do",
        type: "post",
        dataType: "json",
        data: {
            "userid": userid,
        },
        success: function(res) {
        	console.log(res)
        	if( res.code == 200 ){

        		//右下角表格
                for( var i =0; i< res.schoolList.length;i++){
                    var qhtml = ''
                        qhtml += '<tr>'+
                            '<td>'+res.schoolList[i].name+'</td>'+
                            '<td>'+res.schoolList[i].number+'</td>'+
                            '<td>'+res.schoolList[i].change+'</td>'+
                            '<td>'+res.schoolList[i].lastIncrease+'</td>'+
                            '<td>'+res.schoolList[i].increase+'</td>'+
                            '</tr>';
                    $(".table-happening").append(qhtml)
                }

                //左上角柱状图
                var classname = [];
				var male = [];
				var female = [];
				var total = [];
				for(var i = 0; i< res.classList.length;i++){
			    	classname.push(res.classList[i].classname);
			    	male.push(res.classList[i].male);
			    	female.push(res.classList[i].female);
					total.push(res.classList[i].total);
		    	}
	    		//柱状图
			    $('#container').highcharts({
			        chart: {
			            type: 'column',
			            backgroundColor: 'rgba(0,0,0,0)'
			        },
			        title: {
			            text: null
			        },
			        xAxis: {
			            categories: classname,
			            labels: {
			                style: {
			                  color: '#fff',
			                },
			            }
			        },
			        yAxis: {
			            min: 0,//纵轴的最小值
			            gridLineColor: 'transparent', 
			            title: {
			                text: null,
			            },
			            labels: {
			                style: {
			                  color: '#fff',
			                },
			            }
			        },
			        plotOptions: {
			            column: {
			                borderColor: 'transparent'//去边框
			            }
			        },
			        legend: {                                                                    
			            align: 'right', //程度标的目标地位
			            verticalAlign: 'top', //垂直标的目标地位
			            x: -200, //间隔x轴的间隔
			            y: 10, //间隔Y轴的间隔  
			            symbolRadius: 0,
			            itemStyle: {
			                color: '#fff',
			                fontFamily: "Microsoft YaHei",
			                fontWeight: 'normal',
			                fontSize: '12px'
			            },
			            itemHoverStyle: {
			                color: '#fff'
			            }
			        },  
			        tooltip: {
			            // head + 每个 point + footer 拼接成完整的 table
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			            '<td style="padding:0"><b>{point.y:.1f} 名</b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        series: [{
			            name: '男',
			            data: male,
			            color: '#38dc98',
			        },{
			            name: '女',
			            data: female,
			            color: '#40bad0'
			        },{
			            name: '全部',
			            data: total,
			            color: '#3f95f6'
			        }],
			        exporting:{
			            enabled: false
			        },
			        credits: {
			            enabled: false
			        },
			    });


			    //左下角饼状图
			    //圆形进度条
			    var chart1 = Highcharts.chart('ring1', {
					chart: {
						spacing : [40, 0 , 40, 0],
						backgroundColor: 'rgba(0,0,0,0)',
					},
					title: {
						floating: true,
						text: ' ',
						align: 'center',
						verticalAlign: 'bottom',
						style: {
							color: '#fff',
							fontSize: '12px'
						}
					},
					tooltip: {
						pointFormat:'{point.percentage:.1f}%'
					},
					colors: [
			            '#ffc875',
			            '#354357',
			        ],
			        
					plotOptions: {
						pie: {
							borderColor: 'transparent',//去边框
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: false
							},
						}
					}, 
					series: [{
						type: 'pie',
						innerSize: '80%',
						name: '机构状况',
						data: [
							['办公园',  res.totalList[0].percent],
							['私立园',  100-res.totalList[0].percent]
						]
					}],
					exporting:{
			            enabled: false
			        },
			        credits: {
			            enabled: false
			        },
				});

			    //园长状况
			    var chart2 = Highcharts.chart('ring2', {
					chart: {
						spacing : [40, 0 , 40, 0],
						backgroundColor: 'rgba(0,0,0,0)',
					},
					title: {
						floating: true,
						text: ' ',
						align: 'center',
						verticalAlign: 'bottom',
						style: {
							color: '#fff',
							fontSize: '12px'
						}
					},
					tooltip: {
						pointFormat:'{point.percentage:.1f}%'
					},
					colors: [
			            '#f3595b',
			            '#354357',
			        ],
			        
					plotOptions: {
						pie: {
							borderColor: 'transparent',//去边框
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: false
							},	
						}
					}, 
					series: [{
						type: 'pie',
						innerSize: '80%',
						name: '园长状况',
						data: [
							['获得资格证',  res.totalList[1].percent],
							['未获得资格证',  100-res.totalList[1].percent]
						]
					}],
					exporting:{
			            enabled: false
			        },
			        credits: {
			            enabled: false
			        },
				});

			    //老师状态
				var chart3 = Highcharts.chart('ring3', {
					chart: {
						spacing : [40, 0 , 40, 0],
						backgroundColor: 'rgba(0,0,0,0)',
					},
					title: {
						floating: true,
						text: ' ',
						align: 'center',
						verticalAlign: 'bottom',
						style: {
							color: '#fff',
							fontSize: '12px'
						}
					},
					tooltip: {
						pointFormat:'{point.percentage:.1f}%'
					},
					colors: [
			            '#83d7c0',
			            '#354357',
			        ],
			        
					plotOptions: {
						pie: {
							borderColor: 'transparent',//去边框
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: false
							},
						}
					}, 
					series: [{
						type: 'pie',
						innerSize: '80%',
						name: '老师状态',
						data: [
							['获得资格证',  res.totalList[2].percent],
							['未获得资格证',  100-res.totalList[2].percent]
						]
					}],
					exporting:{
			            enabled: false
			        },
			        credits: {
			            enabled: false
			        },
				});

				//男女比例
				var chart4 = Highcharts.chart('ring4', {
					chart: {
						spacing : [40, 0 , 40, 0],
						backgroundColor: 'rgba(0,0,0,0)',
					},
					title: {
						floating: true,
						text: ' ',
						align: 'center',
						verticalAlign: 'bottom',
						style: {
							color: '#fff',
							fontSize: '12px'
						}
					},
					tooltip: {
						pointFormat:'{point.percentage:.1f}%'
					},
					colors: [
			            '#c883d7',
			            '#354357',
			        ],
			        
					plotOptions: {
						pie: {
							borderColor: 'transparent',//去边框
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: false
							},
						}
					}, 
					series: [{
						type: 'pie',
						innerSize: '80%',
						name: '男女比例',
						data: [
							['男',  res.totalList[3].percent],
							['女',  100-res.totalList[3].percent]
						]
					}],
					exporting:{
			            enabled: false
			        },
			        credits: {
			            enabled: false
			        },
				});


				//右上角圆形图
				//圆形图
			    $('#container1').highcharts({
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false,
			            backgroundColor: 'rgba(0,0,0,0)',
			            type: 'pie'
			        },
			        title: {
			            text: null
			        },
			        tooltip: {
			            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			        },
			        colors: [
			            '#38dc98',
			            '#40bad0',
			            '#3f95f6',
			        ],
			        plotOptions: {
			            pie: {
			                borderColor: 'transparent',//去边框
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    format: '{point.percentage:.1f} %',
			                    color: 'white',
			                }
			            }
			        },
			        series: [{
			            name: '占比',
			            colorByPoint: true,
			            data: [{
			                name: '男',
			                y: res.malePercent
			            }, {
			                name: '女',
			                y: res.femalePercent
			            }]
			        }],
			        exporting:{
			            enabled: false
			        },
			        credits: {
			            enabled: false
			        },
			    });

            }else{
                layer.msg(''+res.code+'', {time: 1000});
            }
    	}
	})

})