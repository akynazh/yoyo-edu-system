baseurl = 'http://localhost:9090';
cardId = '20009100359';
interval = 3000;

// http://localhost:63342/yoyo-edu-system/yoyo-view/index.html?id=20009200657
// http://localhost:63342/yoyo-edu-system/yoyo-view/index.html?id=20009100359
// http://localhost:63342/yoyo-edu-system/yoyo-view/index.html?id=123456
const currentPageUrl = window.location.href;
const urlSearchParams = new URLSearchParams(currentPageUrl.split('?')[1]);
const id = urlSearchParams.get('id');
if (id) {
    cardId = id;
    console.log(cardId);
}


// 心率、血氧、微循环
function refreshData1() {
    axios.get(`${baseurl}/e/queryLast/1?cardId=${cardId}`)
        .then(resp => {
            let data = resp.data['obj'];
            let time = "记录时间：" + new Date(data['time']).toLocaleString();
            document.getElementById('heartRate').innerText = data['heartRate'];
            document.getElementById('heartRate_time').innerText = time;
            document.getElementById('bloodOxygen').innerText = data['bloodOxygen'];
            document.getElementById('bloodOxygen_time').innerText = time;
            document.getElementById('microCirculation').innerText = data['microCirculation'];
            document.getElementById('microCirculation_time').innerText = time;
        })
        .catch(err => {
            console.log(err);
        })
}

// 手环肌电
function refreshData2() {
    var myChart1 = echarts.init(document.getElementById('chartmain_zhe1'));
    const currentDate = new Date();
    // const formattedTime = `${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
    const timestampInSeconds = Math.floor(currentDate.getTime() / 1000);
    currentDate.setMinutes(currentDate.getMinutes() - 1);
    // const formattedTime1 = `${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
    const timestampInSeconds1 = Math.floor(currentDate.getTime() / 1000);
    let url = `${baseurl}/e/query/2?cardId=${cardId}&startTime=${timestampInSeconds1}&endTime=${timestampInSeconds}`;
    axios.get(url)
        .then(resp => {
            let data = resp.data['obj'];
            data = data.slice(-6);
            let x_data_list = [];
            let y_data_list_above = [];
            let y_data_list_below = [];
            for (let i = 0; i < data.length; i++) {
                let inputTimeString = data[i]['time'];
                const localeTimeString = new Date(inputTimeString).toLocaleTimeString('zh', {timeZone: 'Asia/Shanghai'});
                x_data_list.push(localeTimeString)
                y_data_list_above.push(data[i]['above']);
                y_data_list_below.push(data[i]['below']);
            }
            var option1 = {
                title: {
                    // text: '肌电'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    textStyle: {
                        color: '#fff',
                        fontSize: 12,
                    },
                    right: '10%',
                    data: ['上方肌电', '下方肌电']
                },
                grid: {
                    x: 40,
                    y: 40,
                    x2: 25,
                    y2: 20,
                },
                toolbox: {
                    feature: {
                        //saveAsImage: {}
                    }
                },
                xAxis: {
                    name: 'T',
                    type: 'category',
                    boundaryGap: true,
                    axisLabel: {
                        /*inside: true,*/
                        interval: 0,
                        textStyle: {
                            color: '#fff',
                            fontSize: 12

                        }
                    },
                    axisTick: {
                        show: false,
                    },
                    axisLine: {
                        show: true,
                        symbol: ['none', 'arrow'],
                        symbolOffset: 12,
                        lineStyle: {
                            color: '#fff',
                        }
                    },
                    data: x_data_list
                },
                yAxis: {
                    name: '电压值(V)',
                    type: 'value',
                    axisLine: {
                        show: true,
                        symbol: ['none', 'arrow'],
                        symbolOffset: 12,
                        lineStyle: {
                            color: '#fff',
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#fff',
                            fontSize: 12
                        }
                    }
                },
                series: [
                    {
                        name: '上方肌电',
                        smooth: true,
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_above,
                        itemStyle: {
                            normal: {
                                color: "#0efdff",//折线点的颜色
                                lineStyle: {
                                    color: "#0efdff",//折线的颜色
                                    width: 2,
                                }
                            },
                        }
                    },
                    {
                        name: '下方肌电',
                        smooth: true,
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_below
                    },
                ]
            };
            myChart1.setOption(option1);
        })
        .catch(err => {
            console.log(err);
        })
}

// 手环血压
function refreshData3() {
    var myChart2 = echarts.init(document.getElementById('chartmain_zhe'));
    const currentDate = new Date();
    // const formattedTime = `${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
    const timestampInSeconds = Math.floor(currentDate.getTime() / 1000);
    currentDate.setMinutes(currentDate.getMinutes() - 1);
    // const formattedTime1 = `${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
    const timestampInSeconds1 = Math.floor(currentDate.getTime() / 1000);
    let url = `${baseurl}/e/query/3?cardId=${cardId}&startTime=${timestampInSeconds1}&endTime=${timestampInSeconds}`;
    axios.get(url)
        .then(resp => {
            let data = resp.data['obj'];
            data = data.slice(-6);
            let x_data_list = [];
            let y_data_list_shrink = [];
            let y_data_list_diastolic = [];
            for (let i = 0; i < data.length; i++) {
                let inputTimeString = data[i]['time'];
                const localeTimeString = new Date(inputTimeString).toLocaleTimeString('zh', {timeZone: 'Asia/Shanghai'});
                x_data_list.push(localeTimeString)
                y_data_list_shrink.push(data[i]['shrink']);
                y_data_list_diastolic.push(data[i]['diastolic']);
            }
            var option2 = {
                title: {
                    // text: '手环血压'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    textStyle: {
                        color: '#fff',
                        fontSize: 12,
                    },
                    right: '10%',
                    data: ['收缩压', '舒张压']
                },
                grid: {
                    x: 40,
                    y: 40,
                    x2: 25,
                    y2: 20,
                },
                toolbox: {
                    feature: {
                        //saveAsImage: {}
                    }
                },
                xAxis: {
                    name: 'T',
                    type: 'category',
                    boundaryGap: true,
                    axisLabel: {
                        /*inside: true,*/
                        interval: 0,
                        textStyle: {
                            color: '#fff',
                            fontSize: 12
                        }
                    },
                    axisTick: {
                        show: false,
                    },
                    axisLine: {
                        show: true,
                        symbol: ['none', 'arrow'],
                        symbolOffset: 12,
                        lineStyle: {
                            color: '#fff',
                        }
                    },
                    data: x_data_list
                },
                yAxis: {
                    type: 'value',
                    name: '血压值(mmHg)',
                    axisLine: {
                        show: true,
                        symbol: ['none', 'arrow'],
                        symbolOffset: 12,
                        lineStyle: {
                            color: '#fff',
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#fff',
                            fontSize: 12
                        }
                    }
                },
                series: [
                    {
                        name: '收缩压',
                        smooth: true,
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_shrink,
                        itemStyle: {
                            normal: {
                                color: "#0efdff",//折线点的颜色
                                lineStyle: {
                                    color: "#0efdff",//折线的颜色
                                    width: 2,
                                }
                            },
                        }
                    },
                    {
                        name: '舒张压',
                        smooth: true,
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_diastolic
                    },
                ]
            };
            myChart2.setOption(option2);
        })
        .catch(err => {
            console.log(err);
        })
}

// muse
function refreshData4() {
    var myChart_muse = echarts.init(document.getElementById('muse'));
    const currentDate = new Date();
    // const formattedTime = `${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
    const timestampInSeconds = Math.floor(currentDate.getTime() / 1000);
    currentDate.setMinutes(currentDate.getMinutes() - 1);
    // const formattedTime1 = `${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
    const timestampInSeconds1 = Math.floor(currentDate.getTime() / 1000);
    let url = `${baseurl}/e/query/4?cardId=${cardId}&startTime=${timestampInSeconds1}&endTime=${timestampInSeconds}`;
    axios.get(url)
        .then(resp => {
            let data = resp.data['obj'];
            data = data.slice(-4);
            let x_data_list = [];
            let y_data_list_leftHead = [];
            let y_data_list_rightHead = [];
            let y_data_list_leftEar = [];
            let y_data_list_rightEar = [];

            for (let i = 0; i < data.length; i++) {
                let inputTimeString = data[i]['time'];
                const localeTimeString = new Date(inputTimeString).toLocaleTimeString('zh', {timeZone: 'Asia/Shanghai'});
                x_data_list.push(localeTimeString)
                y_data_list_leftHead.push(data[i]['leftHead']);
                y_data_list_rightHead.push(data[i]['rightHead']);
                y_data_list_leftEar.push(data[i]['leftEar']);
                y_data_list_rightEar.push(data[i]['rightEar']);
            }
            var option_muse = {
                title: {
                    // text: 'Muse'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    textStyle: {
                        color: '#fff',
                        fontSize: 12,
                    },
                    right: '10%',
                    data: ['左前额', '右前额', '左耳后', '右耳后'],
                },
                grid: {
                    x: 40,
                    y: 40,
                    x2: 25,
                    y2: 20,
                },
                toolbox: {
                    feature: {
                        //saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    name: 'T',
                    boundaryGap: true,
                    axisLabel: {
                        /*inside: true,*/
                        interval: 0,
                        textStyle: {
                            color: '#fff',
                            fontSize: 12
                        }
                    },
                    axisTick: {
                        show: false,
                    },
                    axisLine: {
                        show: true,
                        symbol: ['none', 'arrow'],
                        symbolOffset: 12,
                        lineStyle: {
                            color: '#fff',
                        }
                    },
                    data: x_data_list
                },
                yAxis: {
                    type: 'value',
                    name: '距离(cm)',
                    axisLine: {
                        show: true,
                        symbol: ['none', 'arrow'],
                        symbolOffset: 12,
                        lineStyle: {
                            color: '#fff',
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#fff',
                            fontSize: 12
                        }
                    }
                },
                series: [
                    {
                        name: '左前额',
                        smooth: true,
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_leftHead,
                        itemStyle: {
                            normal: {
                                color: "#0efdff",//折线点的颜色
                                lineStyle: {
                                    color: "#0efdff",//折线的颜色
                                    width: 2,
                                }
                            },
                        }
                    },
                    {
                        name: '右前额',
                        smooth: true,
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_rightHead
                    },
                    {
                        name: '左耳后',
                        smooth: true,
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_leftEar
                    },
                    {
                        name: '右耳后',
                        smooth: true,
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_rightEar
                    },
                ]
            };
            myChart_muse.setOption(option_muse);
        })
        .catch(err => {
            console.log(err);
        })
}

// photo
function refreshData5() {
    axios.get(`${baseurl}/e/queryLast/5?cardId=${cardId}`)
        .then(resp => {
            let data = resp.data['obj'];
            let time = new Date(data['time']).toLocaleString();
            let img = data['imagePath'];
            let remark = data['remark'];
            document.getElementById('my-img-time').innerText = time;
            document.getElementById('my-img-remark').innerText = remark;
            document.getElementById('my-img').src = img;
        })
        .catch(err => {
            console.log(err);
        })
}

// 3d 姿态检测
function refreshData6() {
    var myChart_3d = echarts.init(document.getElementById('g3d'));
    const currentDate = new Date();
    // const formattedTime = `${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
    const timestampInSeconds = Math.floor(currentDate.getTime() / 1000);
    currentDate.setMinutes(currentDate.getMinutes() - 1);
    // const formattedTime1 = `${currentDate.getHours()}:${currentDate.getMinutes()}:${currentDate.getSeconds()}`;
    const timestampInSeconds1 = Math.floor(currentDate.getTime() / 1000);
    let url = `${baseurl}/e/query/6?cardId=${cardId}&startTime=${timestampInSeconds1}&endTime=${timestampInSeconds}`;
    axios.get(url)
        .then(resp => {
            let data = resp.data['obj'];
            data = data.slice(-4);
            let x_data_list = [];
            let y_data_list_result = [];
            for (let i = 0; i < data.length; i++) {
                let inputTimeString = data[i]['time'];
                const localeTimeString = new Date(inputTimeString).toLocaleTimeString('zh', {timeZone: 'Asia/Shanghai'});
                x_data_list.push(localeTimeString)
                y_data_list_result.push(data[i]['result']);
            }
            var option3D = {
                title: {
                    // text: '3d 姿态检测'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    textStyle: {
                        color: '#fff',
                        fontSize: 12,
                    },
                    right: '10%',
                    data: ['姿态信息']
                },
                grid: {
                    x: 40,
                    y: 40,
                    x2: 25,
                    y2: 20,
                },
                toolbox: {
                    feature: {
                        //saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: true,
                    name: 'T',
                    axisLabel: {
                        /*inside: true,*/
                        interval: 0,
                        textStyle: {
                            color: '#fff',
                            fontSize: 12
                        }
                    },
                    axisTick: {
                        show: false,
                    },
                    axisLine: {
                        show: true,
                        symbol: ['none', 'arrow'],
                        symbolOffset: 12,
                        lineStyle: {
                            color: '#fff',
                        }
                    },
                    data: x_data_list
                },
                yAxis: {
                    type: 'value',
                    name: '姿态信息',
                    axisLine: {
                        show: true,
                        symbol: ['none', 'arrow'],
                        symbolOffset: 12,
                        lineStyle: {
                            color: '#fff',
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#fff',
                            fontSize: 12
                        }
                    }
                },
                series: [
                    {
                        name: '姿态信息',
                        type: 'line',
                        stack: '总量',
                        data: y_data_list_result,
                        itemStyle: {
                            normal: {
                                color: "#0efdff",//折线点的颜色
                                lineStyle: {
                                    color: "#0efdff",//折线的颜色
                                    width: 2,
                                }
                            },
                        }
                    },
                ]
            };
            myChart_3d.setOption(option3D);
        })
        .catch(err => {
            console.log(err);
        })
}

// 用户信息
function getUserData() {
    axios.get(`${baseurl}/u/${cardId}`)
        .then(resp => {
            console.log(resp)
            let data = resp.data['obj'];
            document.getElementById('cardId').innerText = data['cardId'];
            document.getElementById('name').innerText = data['name'];
            document.getElementById('major').innerText = data['major'];
            document.getElementById('college').innerText = data['college'];
            document.getElementById('department').innerText = data['department'];
            document.getElementById('classId').innerText = data['classId'];
        })
        .catch(err => {
            console.log(err);
        })
}

function refreshData() {
    refreshData1();
    refreshData2();
    refreshData3();
    refreshData4();
    refreshData5();
    refreshData6();
}

window.onload = function () {
    getUserData();
    refreshData();
}

setInterval(() => {
    refreshData();
}, interval);
