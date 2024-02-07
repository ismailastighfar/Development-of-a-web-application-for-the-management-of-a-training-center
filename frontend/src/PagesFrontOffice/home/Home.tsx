import { useState, useEffect, useRef, ChangeEvent } from "react";
import { useNavigate } from "react-router-dom";
import {
  TrainingPage,
  GetTrainingList,
  GetIndividualTrainings,
  EnrollToTraining,
} from "../../hooks/FO_TrainingList";
import { useAuth } from "../../context/UserContext";
import { PageResponse } from "../../Common/PageResponseAPI";
import Masonry, { ResponsiveMasonry } from "react-responsive-masonry";
import Card, { CardData } from "../../components/Card";
import Popup from "../../components/Popup";
import { getCategories } from "../../hooks/TraininAPI";
import ConfirmationPopup from "../../components/Confirmation";

const frontHome = () => {
  interface DropdownData {
    id: number;
    name: string;
  }

  const { user } = useAuth();

  console.log(user);

  const navigate = useNavigate();

  const [trainingPage, settrainingPage] = useState<TrainingPage>({
    category: "",
    city: "",
    startDate: "",
    page: 0,
    size: 10,
    sort: "",
  });

  const [CategoriesList, setCategoriesList] = useState<DropdownData[]>([]);
  const [trainingPageData, setTrainingPageData] = useState<PageResponse>();
  let userEnrolledTraining: number[] = [];
  const [showLoginPopup, setShowLoginPopup] = useState(false);
  const isDataFetched = useRef(false);
  const [showConfirmationPopup, setShowConfirmationPopup] = useState(false);
  const [SelcetedTrainingIndex, setSelcetedTrainingIndex] = useState<number>(0);

  const fetchTrainingList = async () => {
    try {
      let response = await GetTrainingList(trainingPage);
      response.content = response.content.filter(
        (training: any) =>
          !training.forCompany && !userEnrolledTraining.includes(training.id)
      );
      setTrainingPageData(response);
    } catch (error) {
      alert("Error fetching training list");
    }
  };

  const fetchUserEnrolledTraining = async () => {
    if (user) {
      try {
        const response = await GetIndividualTrainings(user.id);
        // console.log("user list is : ",response);
        userEnrolledTraining = response.map((training: any) => training.id);
      } catch (error) {}
    }
  };

  const fetchCategories = async () => {
    const Categoriesresponce = await getCategories();
    console.log("categories : ", Categoriesresponce);
    const dropdownDataCategories = Categoriesresponce.map((category: any) => ({
      id: 1,
      name: category || "",
    }));

    setCategoriesList(dropdownDataCategories);
  };

  const fetchData = async () => {
    await fetchUserEnrolledTraining(); // Wait for user enrolled training to finish
    await fetchTrainingList(); // Then fetch training list
  };

  useEffect(() => {
    if (!isDataFetched.current) {
      fetchCategories();
      fetchData();
      isDataFetched.current = true;
    }
  }, []);

  const handleEnrollToTraining = (index: number) => {
    setSelcetedTrainingIndex(index);
    if (!user)
      setShowLoginPopup(true);
    else 
      setShowConfirmationPopup(true);
  };


  const enrollToTrainingOnClick = () => {
    console.log(SelcetedTrainingIndex);
    const trainingId = trainingPageData?.content[SelcetedTrainingIndex].id;
    if (!user) return;
    const IndvidualId = user.id;  
    const enrollToTraining = async () => {
      try {
        const response = await EnrollToTraining(trainingId, IndvidualId);
        console.log(response);
        fetchData();
        alert("You have successfully enrolled to the training");
      } catch (error) {
        alert("Error enrolling to the training");
      }
    };
    enrollToTraining();
    setShowConfirmationPopup(false);
  };

  // Handle form field changes
  const handleSearchOnChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;

    settrainingPage((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSearchOnClick = () => {
    settrainingPage((prevData) => ({
      ...prevData,
      page: 0,
    }));
    fetchTrainingList();
  };

  const hadleCategoryDropdownChange = (
    e: React.ChangeEvent<HTMLSelectElement>
  ) => {
    const selectedCategory = e.target.value;
    settrainingPage({
      ...trainingPage,
      category: selectedCategory,
    });
  };


  return (
    <>
      <h1>Trainings</h1>
      <div className="search">
        <div className="form-group">
          <label htmlFor="category">Category</label>
          <select
            id="category"
            name="category"
            value={trainingPage.category}
            className="form-control"
            onChange={hadleCategoryDropdownChange}
            required={true}
          >
            <option value="0"> </option>
            {CategoriesList.map((dropdownItem) => (
              <option key={dropdownItem.id} value={dropdownItem.name}>
                {dropdownItem.name}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="search">City</label>
          <input
            type="text"
            name="city"
            value={trainingPage.city}
            placeholder="city"
            id="city"
            className="form-control"
            onChange={handleSearchOnChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="search">Start Date</label>
          <input
            type="date"
            name="startDate"
            value={trainingPage.startDate}
            placeholder="start date"
            id="startDate"
            className="form-control"
            onChange={handleSearchOnChange}
          />
        </div>
        <button className="btn" onClick={handleSearchOnClick}>
          Search
        </button>
      </div>
      {trainingPageData && (
        <ResponsiveMasonry
          columnsCountBreakPoints={{ 600: 1, 1200: 2, 1550: 3 }}
        >
          <Masonry gutter="40px">
            {trainingPageData.content.map((Training, index) => {
              const cardsData: CardData = {
                id: Training.id,
                title: Training.title,
                content: Training.detailed_program,
                const: Training.cost.toString(),
                hours: Training.hours.toString(),
                category: Training.category,
                city: Training.city,
                endEntrollData: Training.endEnrollDate,
                SubmitOnClick: () => {
                  handleEnrollToTraining(index);
                },
              };
              return <Card {...cardsData} key={index} />;
            })}
          </Masonry>
        </ResponsiveMasonry>
      )}
      {showLoginPopup && (
        <Popup
          Header="Login"
          Content="Please Loging or create an account to enroll to a training"
          Actions={
            <>
              <button className="btn" onClick={() => setShowLoginPopup(false)}>
                Cancel
              </button>
              <button
                className="btn btn-primary"
                onClick={() => navigate("/auth")}
              >
                Login
              </button>
            </>
          }
          IsOpen={true}
          OnClose={() => setShowLoginPopup(false)}
        />
      )}
      {showConfirmationPopup && (
        <ConfirmationPopup
          IsOpen={showConfirmationPopup}
          content="Are you sure you want to enroll to this training"
          onConfirm={() => {
            enrollToTrainingOnClick();
          }}
          cancelOnClick={() => setShowConfirmationPopup(false)}
        />
      )}
    </>
  );
};

export default frontHome;
