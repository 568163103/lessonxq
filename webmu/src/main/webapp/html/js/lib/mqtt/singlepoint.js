$(function () {
  function createMqtt(obj) {
    var client = new Paho.MQTT.Client(obj.host, Number(obj.port), String(obj.clientId));
    client.onConnectionLost = function (err) { if (err.errorCode !== 0) { console.log("失去连接"); } };
    client.onMessageArrived = onMessageArrived;
    client.connect({
      onSuccess: function () { client.subscribe(topic); },
      onFailure: function () { console.log("连接失败"); }
    });
    return client
  }



  var topic = '/v1/jd/msg'
  var client = createMqtt({
    clientId: new Date().getTime(),
    host: '59.110.173.27',
    port: '8083',
    topic: topic
  })

  // 当信息到达时调用
  function onMessageArrived(message) {
    console.log('收到信息')
    // console.log(message.payloadString)
  }

  // $('.sendMsg').click(function () {//发送
    client.send(topic, 'message2')
  // })
})