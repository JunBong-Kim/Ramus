# Ramus
A rolling stone does not rust

주제 : 코로나 대유행 속 모두가 방역의 주체가 되어 안전하고 건강한 도서관 형성에 도움을 주는 종합 어플리케이션

* 아래 엑박뜨는 그림을 클릭하면 설명과 함께 실제 앱 구동 사진이 첨부되어 있습니다 *



상황 분석


	1.  사회적 거리두기 시행때문에  현재 중앙 도서관 신관의 좌석이 절반의 수준이고 컴퓨터를 쓸 수 있는 S Lounge 와 같은 공간은 항상
	    자리가 부족함.
  
	2. 도서관 내, 마스크 미착용(턱스크) 의 상황은 서로간에 매우 민감한 부분이 틀림없으나, 제재를 쉽게 할 수 없음.
  
	3. 밀폐된 도서관에서 코로나 확진자 발생시, 동선에 대한 파악이 쉽지 않고, 연쇄 감염에 대한 위험이 높음. 
	   또한 신관에 정기적으로 자리를 맡아서 공부하는 학생의 비율이 일년에 드물게 사용하는 학우들의 비율보다 높다고 생각되지만,
	   학습한 좌석에 대한 기록이 없음.
     
	4. 학교내 학우들이 경북대학교 코로나 19 대응 관련 공지 사이트가 있음에도, 사이트에 시간을 투자해서 찾아가는 경우가 거의 없다고 생각됨 .
  
    *   이러한 상황을 고려해 봤을 때   도서관 좌석 예약 앱외에도 코로나19 방역에  도움이 되고, 자발적인 관리가 가능한 도서관 앱이 함께 사용된다면, 좀 더
	안전한 도서관 라이프를 즐길 수 있다고 생각함.



전체 기능



	1.QR 시트 

	    저희 람머스 팀은 좌석 배치 시스템에 값싼 QR코드 스티커를 접목시켰습니다.
	    서버를 사용한 이 기능은 QR코드 속에 도서관 좌석에 대한 문자를 숨겨두어 스캔했을시 그 좌석을 즉시 인식하여  사용자에게
	    좌석이 배정받게 되어있습니다. 이를 이용해 보다 직관적으로 반드시 사용할 학생에게 열람실 좌석을 제공해주는 더 나은 사용자 경험을 선사합니다.
<img src="https://github.com/JunBong-Kim/Ramus/issues/1#issue-950811740" width="40%" height="30%" title="px(픽셀) 크기 설정" alt=“main”></img>

	2.현재 남은 좌석 보기

	    저희 람머스 팀은 실시간으로 현재 남은 좌석을 층별, 열람실별로 구분하여 사용자에게 남은 좌석의 수와 좌석 레이아웃을 제공합니다.
	    좌석 레이아웃은 실제 경북대학교 중앙도서관 열람실중 하나를 모델로 제작하였으며 통로 및 기둥등 주위 사물을 통해
	    사용자에게 더 직관적으로 좌석 배치에 대한 인식을 경험하게끔 유도했습니다.

<img src="https://github.com/JunBong-Kim/Ramus/issues/2#issue-950823994" width="40%" height="30%" title="px(픽셀) 크기 설정" alt=“seat_deatil”></img>


	3 이용중 마스크써주세요, 정숙해주세요 , 자리를 너무비운다 쪽지기능

	    저희 람머스팀은 도서관 사용자들이 턱스크 사용자들, 잦은 마스크 미사용 자들에게 어떤식으로 제재를 가해야하는지 고민했습니다.
	    매우 민감한 상황이나 서로가 얼굴을 붉힐 수 도 있는 일이기에, 앱 사용자들이 익명으로 마스크 써주세요 와 같은 알림을 사용해 소통한다면
	    어떨까 하는 생각을 했습니다.
	    익명의 알림은 좌석을 자주 비우는 사용자, 시끄러운 사용자들도 함께 주의를 줄 수 있는 좋은 수단이 될 것입니다.
	    신고 기능의 악용 가능성을 미연에 방지하고자, 좌석등록이 된 상태에서, 같은 열람실 내에서 있는 학우분들에게 익명의 쪽지를 줄 수 있게 구현하였습니다.
<img src="https://github.com/JunBong-Kim/Ramus/issues/3#issue-950835451" width="40%" height="30%" title="px(픽셀) 크기 설정" alt=“warning”></img>


	4 도서관 이용 확진자 사용 내역 확인 및 본인사용 내역, 감염시 다른 이용자들에게 알려주기

	    저희 람머스팀의 앱은 사용자들의 좌석 기록과 시간을 데이터베이스에 저장합니다. 
	    이 데이터는 본인만 볼 수 있으며, 사용자가 코로나19 확진시 본인의 열람실 사용이력을 익명으로 전체 사용자에게 알려줄 수 있습니다.
	    사용자 모두가 익명 감염자의 코로나 확진일과 14일 이내의 도서관 열람실 사용이력을 확인할 수 있으며 
	    앱에 저장된 본인의 열람실 사용 이력과 대조함으로써 더욱 빠르게 대처하고 안심하며 사용할 수 있습니다.
	   
<img src="https://github.com/JunBong-Kim/Ramus/issues/4#issue-950837713" width="40%" height="30%" title="px(픽셀) 크기 설정" alt=“infected”></img>


	5 코로나 인포 & 대구시 재난 문자

	     코로나 4차 대유행속에 살아가는 우리지만 날이 지날수록 모두 지켜가며 방역에대한 경각심이 떨어져가고있습니다.
	     저희는 One Click 으로 여러가지 방식의 코로나 정보를 사용자들에게 제공합니다.
	     저희코로나 인포 탭에서는 경북대학교 방역상황 , 질병관리청 코로나 현황 , 대구,경북 실시간 현황등 앱내에 web 연동을.
	     대구시 재난문자보기는 전국에 발송된 재난문자중 대구광역시에 해당하는 문자를 보여주게 구현했습니다.
	     이를 통해 저희는 빠르고 간편하게 확인할 수 있는 코로나 정보와, 경각심을 더 일깨울 수 있는 등의 부가효과를 기획했습니다.

<img src="https://github.com/JunBong-Kim/Ramus/issues/5#issue-950843964" width="40%" height="30%" title="px(픽셀) 크기 설정" alt=“covidinfo”></img>

	6  경북대 도서관 유투브 인스타 페이스북
	
	    경북대 도서관은 경북대 학우들의 자랑이자 굉장히 유용한 편의공간이자 여가 시설입니다.  이러한 도서관 유투브 계정에 조회수가 1회인 영상이 
	    여러개 인 것을 도서관 정보를 찾다보니 발견했고, 이를 아쉽게 생각한 저희 람머스팀은 도서관 유튜브에 SNS 까지하여 이번 앱 활용의 활성화와 
	    함께 사용자에게 친하게 다가가고자 기능을 추가했습니다

<img src="https://github.com/JunBong-Kim/Ramus/issues/6#issue-950845733" width="40%" height="30%" title="px(픽셀) 크기 설정" alt=“sns”></img>


기대효과와 실용성

	코로나 19상황으로 인해서, 현재 학우들이 사용하는 도서관 관리앱에서 관리해주지 못하는 사각지대를 보안하고자 했습니다. 
	저희가 고안한 기능들은 충분히 좌석 관리의 측면 뿐만	아니라 학생들에게 자율적인 방역, 도서관 환경 조성에 동참하게 만들 수 있고 
	도서관 내 일어난 감염의 상황에 대해서 많은 정보를 줄 수 있습니다. 
	또한 저희 팀 람머스는 데이터가 실시간으로 움직이는, 살아있는 앱을 만들고자 노력하였고 총 15 * 44 = 660 개의 좌석 데이터를 다룰 수 있는
	구조도 적용했습니다. 실제 도서관 관리앱에서 활용하는 약 800~900여 개의 데이터와 비교해도 별 차이가 없는 수치입니다.
	아래는 저희가 개발에 사용한 구조와 패턴, 라이브러리 입니다.
 
    
    
개발에 사용한 구조와 패턴

	1 MVVM Pattern (model-view-viewmodel, MVVM)
	
 	저희는 UI Design 팀과 Function 개발 팀 으로 나누어 진행을 하였습니다. 
 	View 와 Model 이 확실하게 구별되어 있는 MVVM 패턴은 각자의 팀 역할에 충실히 진행할수 있게 하였습니다.
   	또한, Firebase 서버단에서 받는 모든 정보에 대한 부분은 Repository 클래스에서 직접 커스텀한 LiveData를 활용해 ViewModel을 거쳐 
   	view에 알렸습니다. 이러한 방식을 통해 최대한 기능적으로 작은 단위로 나누어 각자의 역할을 수행하였고, 비록 설계단계에서 많은 비용지출이 있었지만,
  	로직의 일관성을 지키면서 무결점성을 최대한 보장하고자 하였습니다.
	 
	 
	 
	2 Observer Pattern 
	
	저희의 앱은 잔여석 확인 또는 각종 상황 데이터 저장 등 실시간으로 진행이 되어야 하는 부분이 여럿 존재합니다. 
  	그렇기에 매순간 변화를 감지하고 알려주는 옵저버 패턴을 통해서 성공적으로 개발을 마무리 할 수 있었습니다.
    	모든 실시간 데이터는 뷰모델에서 감지하는 라이브 데이터를 통해서 관리하였고, 이러한 방식을 통해 모든 UI 관련 작업은 메인쓰레드에서 처리하였습니다.
	ANR(Application Not Responding)등의 메인쓰레드 관련 에러를 방지, 최상의 UI반응성을 유지하고자 노력했습니다.



	3 View & Data Binding
	xml 의 객체를 할당하기 위해 매번 새로운 객체를 생성하는 과정을 생략함으로써 코드가 간결하고 직관적이고 쓸데없는 메모리 낭비를 줄일수 있었습니다.  



	4 DataBase Query ( FireBase Store )
	어플 내 데이터를 담을 DB 로 Google 에서 제공하는 FIreBase Store 를 사용하였습니다. 여러 쿼리문들과 인덱싱을 통해서
  	효율적인 서버 데이터 관리를 할 수 있었습니다.



 
    
Libraries

	저희 어플내에는  smtp(simple mail tramsfer protocol) 을 활용해  메일 송신을 위해 Java Mail Api를 활용했습니다.
 	대구 실시간 재난 문자 현황을 파싱하기 위해서 jsoup 라이브러리를 사용했습니다.
 	QR 코드 사용을 위해 ZXING Library 를 사용했습니다.
 	안드로이드 노티피케이션 알림을 활용하기 위해 Retrofit2 라이브러리를 사용했습니다.
	
    
Future Work

	0. 모든 도서관 좌석에 인쇄한 QR 코드 스티커를 붙이고 상용화를 진행해 보고 싶습니다.

	1-1. 관리자 계정을 만들어 도학위에 전달한 후, 그들로 인한 조금의 강제성을 포함한 주제적 방역을 실시할 수 있도록 하고 싶습니다.

	1-2. 건의사항 함을 만들어 관리자와 소통할 수 있도록 제작해 보고 싶습니다. 

	1-3. 관리자 계정을 통해 신고를 많이 받거나 또는 코로나 확진이 발생한 경우 관리자 계정에서 해당 학우분에게 조치를 취할수 있도록 하고 싶습니다.

	2. 사용자에게 편의를 제공하기 위해서 회원 가입시 lms 연동을 통해 학생증으로 간단한 절차로 가입을 하도록 하고싶습니다.

	3. "신고하기" 기능에 더 많은 상황을 만들어 신고 할수 있도록 하고 싶습니다. 

	3-1 "신고 누적이 월 10회 이상 받은 학생들을 모아 볼 수 있는 현황이 있으면 조금 더 자율적 관리가 되지 않을 까 합니다.

