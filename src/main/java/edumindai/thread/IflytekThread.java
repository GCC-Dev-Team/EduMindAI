package edumindai.thread;

import edumindai.model.entity.Question;
import edumindai.service.impl.IflytekServiceImpl;
import edumindai.service.websocket.IflytekClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class IflytekThread extends Thread {

    private Question question;

    public IflytekThread( Question question) {
        this.question = question;
    }

    @Override
    public void run() {
        IflytekServiceImpl iflytekService = new IflytekServiceImpl();

        iflytekService.sendQuestionAndAnswer(question);
    }

}
