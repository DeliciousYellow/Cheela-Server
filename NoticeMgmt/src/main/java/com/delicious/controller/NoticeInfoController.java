package com.delicious.controller;

import com.delicious.component.RabbitMQ_Notice;
import com.delicious.pojo.entity.notice.NoticeInfo;
import com.delicious.pojo.AddAndEditGroup;
import com.delicious.pojo.Result;
import com.delicious.service.NoticeInfoService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutorService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/noticeInfo")
public class NoticeInfoController extends BaseController<NoticeInfoService, NoticeInfo>{

    @Resource
    private RabbitMQ_Notice rabbitMQNotice;
    @Resource
    private ExecutorService executorService;

    @Override
    @GetMapping("/{id}")
    protected Result baseQueryById(@PathVariable Integer id) {
        return super.baseQueryById(id);
    }

    @Override
    @GetMapping("/")
    protected Result baseQueryByEntity(NoticeInfo noticeInfo) {
        return super.baseQueryByEntity(noticeInfo);
    }

    @Override
    @GetMapping("/{pageIndex}/{pageSize}")
    protected Result baseQueryPageByEntity(NoticeInfo noticeInfo, @PathVariable int pageIndex, @PathVariable int pageSize) {
        return super.baseQueryPageByEntity(noticeInfo, pageIndex, pageSize);
    }

    /**
     * 当有用户发送公告消息后的流程
     * 收到前端新发送的公告消息 -> 把消息发给扇出交换机 -> 扇出到每个noticeMgmt模块对应的队列中
     * -> noticeMgmt集群的每个实例自己的@RabbitListener方法收到消息
     * -> 调用每个实例自己的WebSocket组件的sendMessage方法
     * -> sendMessage方法遍历ConcurrentHashMap取到每个WebSocket连接实例
     * -> 依次给每个已连接的客户端推送公告消息
     */
    @Override
    @PostMapping("/")
    protected Result baseAdd(@RequestBody @Validated(AddAndEditGroup.class) NoticeInfo noticeInfo) {
        Result result = super.baseAdd(noticeInfo);
        executorService.submit(()->rabbitMQNotice.sendMsg(noticeInfo,"INSERT"));
        return result;
    }

    @Override
    @PutMapping("/")
    protected Result baseEdit(@RequestBody @Validated(AddAndEditGroup.class) NoticeInfo noticeInfo) {
        executorService.submit(()->rabbitMQNotice.sendMsg(noticeInfo,"UPDATE"));
        return super.baseEdit(noticeInfo);
    }

    @Override
    @DeleteMapping("/{id}")
    protected Result baseDelById(@PathVariable Integer id) {
        executorService.submit(()->rabbitMQNotice.sendMsg(NoticeInfo.builder().noticeId(id).build(),"DELETE"));
        return super.baseDelById(id);
    }

    @Override
    @DeleteMapping("/")
    protected Result baseDelByIds(@RequestParam("ids") String ids) {
        return super.baseDelByIds(ids);
    }
}
