package kr.co.iei.review.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.review.model.dao.PlanReviewDao;

@Service
public class PlanReviewService {
 @Autowired
 private PlanReviewDao planReviewDao;
}
