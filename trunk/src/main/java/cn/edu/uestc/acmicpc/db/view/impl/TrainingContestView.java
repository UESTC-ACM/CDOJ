package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingContestView extends View<TrainingContest> {

	private Integer trainingContestId;
	private String title;
	private Boolean isPersonal;
	private List<TrainingStatusView> trainingStatusViewList;
	private Integer type;
	private String typeName;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	@Ignore
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTrainingContestId() {
		return trainingContestId;
	}

	public void setTrainingContestId(Integer trainingContestId) {
		this.trainingContestId = trainingContestId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsPersonal() {
		return isPersonal;
	}

	public void setIsPersonal(Boolean personal) {
		isPersonal = personal;
	}

	public List<TrainingStatusView> getTrainingStatusViewList() {
		return trainingStatusViewList;
	}

	@Ignore
	public void setTrainingStatusViewList(
			List<TrainingStatusView> trainingStatusViewList) {
		this.trainingStatusViewList = trainingStatusViewList;
	}

	public TrainingContestView(TrainingContest trainingContest) {
		super(trainingContest);
		List<TrainingStatus> trainingStatusList = (List<TrainingStatus>) trainingContest
				.getTrainingStatusesByTrainingContestId();
		Collections.sort(trainingStatusList, new Comparator<TrainingStatus>() {
			@Override
			public int compare(TrainingStatus a, TrainingStatus b) {
				return a.getRank().compareTo(b.getRank());
			}
		});
		trainingStatusViewList = new LinkedList<>();
		for (TrainingStatus trainingStatus : trainingStatusList)
			trainingStatusViewList.add(new TrainingStatusView(trainingStatus));
		setTypeName(Global.TrainingContestType.values()[trainingContest
				.getType()].getDescription());
	}
}
