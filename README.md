# MinaForAndroid
基于Mina的android通信框架，通过封装实现RPC通信，旨在打造一个如下功能的通信框架：  
1.可扩展的RPC通信，客户端和服务端之间可自定义通信接口(在此套用Android框架层的概念叫做service服务)。自定义接口的创建将尽可能简单，以webservice的创建难度为目标。  
2.指定设备路径上传文件功能，大文件分流上传。
3.推送功能，实现正常状态下的推送与设备休眠状态下的推送。
    
功能仍在完善中。当前实现了半自动化的RPC通信，使用方式如下：  
1.创建一个Service类，继承MinaRPCService,对该类添加注解，typeName不为空且唯一。  

<div class="cnblogs_code">
<pre>@MinaRPCType(typeName = <span style="color: #800000;">"</span><span style="color: #800000;">test</span><span style="color: #800000;">"</span><span style="color: #000000;">)
</span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">abstract</span> <span style="color: #0000ff;">class</span><span style="color: #000000;"> AbstractTestService extends MinaRPCService{
    @MinaRPCMethod
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">abstract</span><span style="color: #000000;"> String sayHello(TestBean testBean);
    @MinaRPCMethod
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">abstract</span> <span style="color: #0000ff;">int</span><span style="color: #000000;"> getAge(TestBean testBean);
}</span></pre>
</div>
<p>&nbsp;</p>

2.在客户端和服务端分别创建一个该类的子类，其中服务端需要写真实的处理逻辑，而客户端在所有的方法内部调用senMsg方法。
<div class="cnblogs_code">
<pre><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">class</span><span style="color: #000000;"> ClientTestService extends AbstractTestService{

    @MinaRPCMethod
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">int</span><span style="color: #000000;"> getAge(TestBean testBean) {
        sendMsg(testBean);
        </span><span style="color: #0000ff;">return</span> <span style="color: #800080;">0</span><span style="color: #000000;">;
    }
    @MinaRPCMethod
    </span><span style="color: #0000ff;">public</span><span style="color: #000000;"> String sayHello(TestBean testBean) {
        </span><span style="color: #008000;">//</span><span style="color: #008000;"> TODO Auto-generated method stub</span>
<span style="color: #000000;">        sendMsg(testBean);
        </span><span style="color: #0000ff;">return</span> <span style="color: #0000ff;">null</span><span style="color: #000000;">;
    }

}</span></pre>
</div>
<p>&nbsp;</p>
目前只支持单个参数的方法，后续会改进。  
3.服务端在启动后，调用addService加入该服务
<div class="cnblogs_code">
<pre><span style="color: #008000;">//</span><span style="color: #008000;">启动mina服务器</span>
        <span style="color: #0000ff;">if</span><span style="color: #000000;"> (MinaServer.serverStart()) {
            MinaServer.addService(</span><span style="color: #0000ff;">new</span><span style="color: #000000;"> ServerTestService());
            System.err.println(</span><span style="color: #800000;">"</span><span style="color: #800000;">mina start success</span><span style="color: #800000;">"</span><span style="color: #000000;">);
        }</span><span style="color: #0000ff;">else</span><span style="color: #000000;"> {
            System.err.println(</span><span style="color: #800000;">"</span><span style="color: #800000;">mina start fail</span><span style="color: #800000;">"</span><span style="color: #000000;">);
        }</span></pre>
</div>
<p>&nbsp;</p>
4.客户端在启动连接后，可在任何地方调用
<div class="cnblogs_code">
<pre><span style="color: #000000;">MinaClient.clientStart();
        TestBean testBean</span>=<span style="color: #0000ff;">new</span><span style="color: #000000;"> TestBean();
        testBean.setAge(</span><span style="color: #800080;">10</span><span style="color: #000000;">);
        testBean.setName(</span><span style="color: #800000;">"</span><span style="color: #800000;">LinJ</span><span style="color: #800000;">"</span><span style="color: #000000;">);
        AbstractTestService testService</span>=<span style="color: #0000ff;">new</span><span style="color: #000000;"> ClientTestService();
        
        testService.getAge(testBean);
        testService.sayHello(testBean);</span></pre>
</div>
<p>&nbsp;</p>
其他详情可见源码test目录。  
注意，客户端是不会有返回值的，因为Mina的发送和返回是异步，在Android端本人通过BroadCast来处理返回信息。  
demo简陋，后续更新完善。
  
2月15更新：先增了一个同步客户端，实现了真正意义上的RPC调用，带有返回值，返回值需要在自定义服务的客户端服务中对返回的Serializable对象强制转换。使用同步客户端将使用短连接方式通信。
