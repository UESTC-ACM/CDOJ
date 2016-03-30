GlobalVariables =
  Version: "V2.3.0"

  OnlineJudgeReturnType:
    OJ_WAIT: 0
    OJ_AC: 1
    OJ_PE: 2
    OJ_TLE: 3
    OJ_MLE: 4
    OJ_WA: 5
    OJ_OLE: 6
    OJ_CE: 7
    OJ_RE_SEGV: 8
    OJ_RE_FPE: 9
    OJ_RE_BUS: 10
    OJ_RE_ABRT: 11
    OJ_RE_UNKNOWN: 12
    OJ_RF: 13
    OJ_SE: 14
    OJ_RE_JAVA: 15
    OJ_JUDGING: 16
    OJ_RUNNING: 17
    OJ_REJUDGING: 18

  ContestType:
    PUBLIC: 0
    PRIVATE: 1
    DIY: 2
    INVITED: 3
    INHERIT: 4
    ONSITE: 5

  ContestStatus:
    PENDING: "Pending"
    RUNNING: "Running"
    ENDED: "Ended"

  AuthenticationType:
    NORMAL: 0
    ADMIN: 1
    CONSTANT: 2
    NOOP: 3
    CURRENT_USER: 4

  AuthorStatusType:
    NONE: 0
    PASS: 1
    FAIL: 2

  ContestRegistryStatus:
    PENDING: 0
    ACCEPTED: 1
    REFUSED: 2

  ArticleType:
    NOTICE: 0
    ARTICLE: 1
    COMMENT: 2

GlobalConditions =
  articleCondition:
    currentPage: null
    startId: undefined
    endId: undefined
    keyword: undefined
    title: undefined
    userId: undefined
    userName: undefined
    type: undefined
    orderFields: "id"
    orderAsc: "false"
  problemCondition:
    currentPage: null
    startId: undefined
    endId: undefined
    title: undefined
    source: undefined
    isSpj: undefined
    startDifficulty: undefined
    endDifficulty: undefined
    keyword: undefined
    orderFields: "id"
    orderAsc: "true"
  contestCondition:
    currentPage: null
    startId: undefined
    endId: undefined
    keyword: undefined
    title: undefined
    orderFields: "time"
    orderAsc: "false"
  statusCondition:
    currentPage: null
    startId: undefined
    endId: undefined
    userName: undefined
    problemId: undefined
    languageId: undefined
    contestId: -1
    result: 0
    orderFields: "statusId"
    orderAsc: "false"
  userCondition:
    currentPage: null
    startId: undefined
    endId: undefined
    userName: undefined
    nickName: undefined
    type: undefined
    school: undefined
    departmentId: undefined
    orderFields: "solved,tried,id"
    orderAsc: "false,false,true"
  teamCondition:
    currentPage: null
    userId: undefined
    leaderId: undefined
    teamName: undefined
    orderFields: "teamId"
    orderAsc: "false"
  messageCondition:
    currentPage: null
    senderId: null
    receiverId: null
    isOpened: null
    userId: null
    userAId: null
    userBId: null
    orderFields: "time"
    orderAsc: "false"
  contestTeamCondition:
    currentPage: null
    contestId: null
    teamId: null
    status: null
    orderFields: "contestTeamId"
    orderAsc: "false"
  trainingCriteria:
    currentPage: null
    startId: null
    endId: null
    keyword: null
    orderFields: "trainingId"
    orderAsc: "false"
  trainingUserCriteria:
    currentPage: null
    startId: null
    endId: null
    trainingId: null
    keyword: null
    type: null
    orderFields: "rank,trainingUserName"
    orderAsc: "true,true"
  trainingContestCriteria:
    currentPage: null
    startId: null
    endId: null
    trainingId: null
    keyword: null
    trainingContestType: null
    trainingPlatformType: null
    orderFields: "trainingContestId"
    orderAsc: "true"
