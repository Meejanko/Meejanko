## 一 · 操作系统导论
[操作系统导论]
### 几种状态
#### interrupt（中断）
中断的常见功能:  
中断是指系统发生某个异步/同步事件后,处理机暂停正在执行的程序,转去执行处理该事件程序的过程.<br/>
通常通过中断向量（中断向量）将中断控制传输到中断服务例程，该中断向量包含所有服务例程的地址<br/>
中断体系结构必须保存被中断指令的地址<br/>
当正在处理另一个中断时，传入中断被禁用，以防止丢失的中断<br/>
一个操作系统是由中断驱动的<br/>
**类型**： [tpyes of interrupt]<br/>
> **外部中断(interrupt)**，异步中断：
  - 外部设备（如鼠标，键盘）所发出的I/O请求
  - 分为**可屏蔽**的和**不可屏蔽**的两类，由一些硬件设备产生,可以在指令执行的**任意时刻**产生.
> **异常(exception)**，内部中断，同步中断：
  - 由**CPU(正在执行的进程**)产生，一条**指令终止执行**后CPU才会发出中断。
  - 常见的异常有除零、溢出及页面异常(fault出错)等。另一种情况是使用int指令(trap陷入)，Linux使用该指令来实现系统调用。 fault与trap区别<br/>

**中断处理**（Interrupt Handling）<br/>
- 操作系统通过存储寄存器和程序计数器来保持CPU的状态。
- 确定发生了哪种类型的中断：
- 通过一个通用例程进行轮询（polling）
- 矢量化中断系统（vectored interrupt system)
■单独的代码段确定对每种类型的中断应该采取什么操作
#### trap（陷入）
陷入（trap）是由错误或用户请求引起的软件生成的中断（software-generated interrupt）<br/>
### 存储结构（storage structure）
> voltage storage (易失性储存器)：
- 寄存器 register
- 高速缓存 cache
- 主存储器 main memory
> nonvoltage storage (非易失性存储器):
- 闪存 nonvoltage memory
- 磁盘 hard-disk drives
- 光盘 optical disk
- 磁带 magnetic tapes
### I/O
> 同步I/O(Synchronous I/O)：在I/O启动后，控制仅在I/O完成时才返回到用户程序
> 异步I/O（Asynchronous I/O）：在I/O启动后，控件返回到用户程序，而不等待I/O完成



[操作系统导论]: https://blog.csdn.net/weixin_53254469/article/details/127234923
[tpyes of interrupt]: https://img-blog.csdnimg.cn/img_convert/53eefb4dda2a25e3e14494d938a4367f.png
