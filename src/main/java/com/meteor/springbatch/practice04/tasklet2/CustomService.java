package com.meteor.springbatch.practice04.tasklet2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomService {

    public void businessLogic() {

        // 비지니스 로직 작성 부분
        for (int idx = 0; idx < 10; idx ++) {
            log.info("[idx] = " + idx);
        }
    }

}
