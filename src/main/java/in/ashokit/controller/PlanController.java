package in.ashokit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.constants.AppConstants;
import in.ashokit.entity.Plan;
import in.ashokit.properties.AppProperties;
import in.ashokit.service.PlanService;

@RestController
public class PlanController {

	private PlanService planService;

	private Map<String, String> messages;

	public PlanController(PlanService planService, AppProperties appProperties) {
		this.planService = planService;
		this.messages = appProperties.getMessages();

	}

	@GetMapping("/getPlanCategories")
	public ResponseEntity<Map<Integer, String>> getPlanCategories() {

		Map<Integer, String> planCategories = planService.getPlanCategories();

		return new ResponseEntity<>(planCategories, HttpStatus.OK);
	}

	@PostMapping("/savePlan")
	public ResponseEntity<String> savePlan(@RequestBody Plan plan) {

		String responseMsg = AppConstants.EMPTY_STR;

		boolean isSavedPlan = planService.savePlan(plan);

		if (isSavedPlan) {
			responseMsg = messages.get(AppConstants.PLAN_SAVED);
		} else {
			responseMsg = messages.get(AppConstants.PLAN_NOT_SAVED);
		}
		return new ResponseEntity<String>(responseMsg, HttpStatus.CREATED);
	}

	@GetMapping("/getAllPlans")
	public ResponseEntity<List<Plan>> getAllPlans() {

		List<Plan> allPlans = planService.getAllPlans();
		return new ResponseEntity<>(allPlans, HttpStatus.OK);
	}

	@GetMapping("/plan/{planId}")
	public ResponseEntity<Plan> editPlan(@PathVariable Integer planId) {

		Plan plan = planService.getPlanById(planId);

		return new ResponseEntity<>(plan, HttpStatus.OK);

	}

	@DeleteMapping("/deletePlan/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId) {

		String msg = AppConstants.EMPTY_STR;

		boolean isDeleted = planService.deletePlanById(planId);

		if (isDeleted) {
			msg = messages.get(AppConstants.PLAN_DELETED);
		} else {
			msg = messages.get(AppConstants.PLAN_NOT_DELETED);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);

	}

	@PutMapping("/updatePlan")
	public ResponseEntity<String> updatePlan(@RequestBody Plan plan) {

		String msg = AppConstants.EMPTY_STR;

		boolean isUpdated = planService.updatePlan(plan);
		if (isUpdated) {
			msg = messages.get(AppConstants.PLAN_UPDATED);
		} else {
			msg = messages.get(AppConstants.PLAN_NOT_UPDATED);

		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@PutMapping("/statusChange/{planId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer planId, @PathVariable String status) {

		String msg = AppConstants.EMPTY_STR;

		boolean isStatusChanged = planService.planStatusChange(planId, status);
		if (isStatusChanged) {
			msg = messages.get(AppConstants.PLAN_STATUS_CHANGED);
		} else {
			msg = messages.get(AppConstants.PLAN_STATUS_NOT_CHANGED);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
