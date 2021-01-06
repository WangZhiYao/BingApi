package me.zhiyao.bing.dao.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import me.zhiyao.bing.dao.mapper.BingImageMapper
import me.zhiyao.bing.dao.model.BingImage
import org.springframework.stereotype.Service

/**
 *
 * @author WangZhiYao
 * @date 2020/12/22
 */
@Service
class BingImageService : ServiceImpl<BingImageMapper, BingImage>()