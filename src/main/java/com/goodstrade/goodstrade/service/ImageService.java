package com.goodstrade.goodstrade.service;

import com.goodstrade.goodstrade.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
}
