package me.zhiyao.bing.dao.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import me.zhiyao.bing.dao.model.BingImage
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

/**
 *
 * @author WangZhiYao
 * @date 2020/12/22
 */
@Mapper
@Repository
interface BingImageMapper : BaseMapper<BingImage> {

    fun random(): BingImage?

    fun getByDate(year: Int, month: Int, day: Int): BingImage?
}