package me.zhiyao.bing.dao.service

import com.baomidou.mybatisplus.extension.service.IService
import me.zhiyao.bing.dao.model.BingImage
import org.springframework.stereotype.Service

/**
 *
 * @author WangZhiYao
 * @date 2020/12/22
 */
interface BingImageService : IService<BingImage> {

    fun getByDate(year: Int, month: Int, day: Int): BingImage?
}