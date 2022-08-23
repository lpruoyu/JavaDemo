package programmer.lp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import programmer.lp.domain.Record;
import programmer.lp.domain.User;
import programmer.lp.mapper.RecordMapper;
import programmer.lp.service.RecordService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

/**
 * 新增借阅记录
 * @param record 新增的借阅记录
 */
@Override
public Integer addRecord(Record record) {
    return recordMapper.addRecord(record);
}

/**
 * 查询借阅记录
 * @param record 借阅记录的查询条件
 * @param user 当前的登录用户
 * @param pageNum 当前页码
 * @param pageSize 每页显示数量
 */
@Override
public PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize) {
    // 设置分页查询的参数，开始分页
    PageHelper.startPage(pageNum, pageSize);
    //如果不是管理员，则查询条件中的借阅人设置为当前登录用户
    if(!"ADMIN".equals(user.getRole())){
        record.setBorrower(user.getName());
    }
    Page<Record> page= recordMapper.searchRecords(record);
    return new PageResult(page.getTotal(),page.getResult());
}

}
