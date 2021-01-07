package me.zhiyao.bing.dao.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import me.zhiyao.bing.dao.mapper.BingImageMapper
import me.zhiyao.bing.dao.model.BingImage
import me.zhiyao.bing.dao.service.BingImageService
import org.springframework.stereotype.Service

/**
 *
 * @author WangZhiYao
 * @date 2021/1/7
 */
@Service
class BingImageServiceImpl : BingImageService, ServiceImpl<BingImageMapper, BingImage>() {

    override fun getByDate(year: Int, month: Int, day: Int): BingImage? {
        val wrapper = KtQueryWrapper(BingImage::class.java)
        wrapper.eq(BingImage::year, year)
            .eq(BingImage::month, month)
            .eq(BingImage::day, day)
            .last("LIMIT 1")

        return getOne(wrapper)
    }
}