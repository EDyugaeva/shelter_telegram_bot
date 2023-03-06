package pro.sky.animal_shelter_telegram_bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.repository.ReportRepository;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Service for working with repository ReportRepository
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {


    private final ReportRepository reportRepository;



    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report addReport(Report report) {
        Report addingReport = reportRepository.save(report);
        log.info("Report with id {} is saved", addingReport);
        return addingReport;
    }

    @Override
    public Report deleteReport(Long id) {
        Report deleteReport = reportRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Report with id = %d is not found", id)));
        reportRepository.deleteById(id);
        log.info("Report with id {} is deleted", id);
        return deleteReport;
    }

    @Override
    public Report findReport(Long id) {
        Report report = reportRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Report with id = %d is not found", id)));

        log.info("Report with id {} is found", id);
        return report;
    }

    @Override
    public Report changeReport(Report report) {
        if (reportRepository.findById(report.getId()).isEmpty()) {
            log.info("Report with id {} is not found", report.getId());
            throw new NoSuchElementException(String.format("Report with id = %d is not found", report.getId()));
        }
        Report changingReport = reportRepository.save(report);
        log.info("Report {} is saved", report);
        return changingReport;
    }

    /**
     * Saving report (or changing) by text from telegram
     *
     * @param text   - from message
     * @param chatId - from update
     * @param date   - local date (now)
     * @return message to send
     */
    @Override
    public String[] setReportToDataBase(String text, Long chatId, String date) {
        log.info("Setting report to database");

        Report report = findReportByChatIdAndDate(chatId, date);

        String[] parsingText = text.split("-");

        if (parsingText.length == 3) {
            report.setHealth(parsingText[0]);
            report.setDiet(parsingText[1]);
            report.setChangeInBehavior(parsingText[2]);
            log.info("Отчет без ошибок запарсен. Состоит из частей: {}, {}, {}", parsingText[0], parsingText[1], parsingText[2]);
        } else {
            log.warn("Mistake in parsing");
            throw new IllegalArgumentException("Mistake in report");
        }

        reportRepository.save(report);
        log.info("Отчет {} сохранен", report.getId());
        return parsingText;

    }

    /**
     * @param chatId - from update
     * @param date   - local date (now)
     * @return report
     */
    @Transactional
    public Report findReportByChatIdAndDate(Long chatId, String date) {
        log.info("Start finding report by chat id and date");
        return reportRepository.findReportByDateOfReportAndPetOwner_ChatId(date, chatId).orElseThrow(() ->
                new NoSuchElementException(String.format("Report with date = %s and chat id = %d is not found", date, chatId)));
    }

    @Override
    public Collection<Report> getUncheckedReports() {
        List<Report> reportsList = new ArrayList<>(reportRepository.getUncheckedReports());
        if (reportsList.isEmpty()) {
            throw new NullPointerException("List of result is empty");
        }
        log.info("Get list of unchecked reports");
        return reportsList;
    }

    /**
     * Set mark to report (will be saved in database)
     *
     * @param id     - report id (will get from telegram chat)
     * @param result - mark
     * @return saved report
     */
    @Override
    public Report setMarkOnReport(Long id, String result) {
        Report report = findReport(id);

        if (result.isEmpty()) {
            log.warn("Result us empty");
            throw new NullPointerException("Result us empty");
        }
        report.setResult(result);
        report.setReportChecked(true);
        log.info("Mark was set on report " + id);
        reportRepository.save(report);
        return report;
    }
}
