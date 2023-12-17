### 几种状态
#### interrupt（中断）
中断的常见功能:  
中断是指系统发生某个异步/同步事件后,处理机暂停正在执行的程序,转去执行处理该事件程序的过程.<br/>
通常通过中断向量（中断向量）将中断控制传输到中断服务例程，该中断向量包含所有服务例程的地址<br/>
中断体系结构必须保存被中断指令的地址<br/>
当正在处理另一个中断时，传入中断被禁用，以防止丢失的中断<br/>
一个操作系统是由中断驱动的<br
types of interrupt: [1]
[1]https://img-blog.csdnimg.cn/img_convert/53eefb4dda2a25e3e14494d938a4367f.png

#### trap（陷入）
陷入（trap）是由错误或用户请求引起的软件生成的中断（software-generated interrupt）<br/>
