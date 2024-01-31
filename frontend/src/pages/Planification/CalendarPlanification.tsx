import React from 'react';
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from '@fullcalendar/timegrid';
import { EventInput } from '@fullcalendar/core/index.js';
import interactionPlugin from '@fullcalendar/interaction';
import { useParams } from 'react-router-dom';
import { useState , useEffect , useRef } from "react";
import Popup from '../../components/Popup';
import { TrainingSessionData , TrainingSessionRequest , SaveTrainingSessionsList , GetTrainingSessionsByTraining } from "../../hooks/TrainingSessionsAPI"
import { TrainingData , getTrainingById } from '../../hooks/TraininAPI';
import {TrainingSession} from '../../components/TrainingSession';
import { info } from 'sass';


const CalendarPlanification: React.FC = () => {

  const { Trainingid } = useParams<{ Trainingid?: string }>();
  const [trainingStartDate, setTrainingStartDate] = useState<string>('');
  const [trainingData, setTrainingData] = useState<TrainingData>();
  const trainingId : number = Trainingid ? parseInt(Trainingid, 10) : 0; 
  console.log("trainingId", trainingId);

  const [trainingSessionData, setTrainingSessionData] = useState<TrainingSessionData>({
        id: 0,
        name: '',
        description: '',
        trainingSessionDate: '',
        StartTime : '',
        EndTime : '',
        IsAllDay : false,
        trainingId: 0,
        IsNew: false,
  });

  const [IsOpenPopup, setIsOpenPopup] = useState(false);

  const [events, setEvents] = useState<EventInput[]>([]);

  const fetchTrainingSessions = async () => {

    const response = await GetTrainingSessionsByTraining(trainingId);

    let eventsList : EventInput[] = [];
    response.data.map((trainingSession : TrainingSessionRequest) => {
      const event : EventInput = {
        id: trainingSession.id.toString(),
        IsNew: false,
        title: trainingSession.name, 
        Startdate: trainingSession.sessionDate,
        start: `${trainingSession.sessionDate}T${trainingSession.sessionStartTime}` , 
        end: `${trainingSession.sessionDate}T${trainingSession.sessionEndTime}`,
        allDay: (trainingSession.sessionStartTime === "06:00:00" && trainingSession.sessionEndTime === "08:00:00"),
        startEventTime: trainingSession.sessionStartTime,
        endEventTime: trainingSession.sessionEndTime,
        description: trainingSession.description,
      };

      eventsList.push(event);
    });

    setEvents(eventsList);
  }

  const fetchTriningData = async () => {

    const response = await getTrainingById(trainingId);
    setTrainingStartDate(response.endEnrollDate);
    setTrainingData(response);
    console.log("response.data.startDate", response.endEnrollDate);
    
  }

  const isDataFetched = useRef(false);

  useEffect(() => {
        if (!isDataFetched.current) {

          if(trainingId !== 0)
            fetchTriningData();
          fetchTrainingSessions();
        }
        isDataFetched.current = true;
    }
  , []);

  const handleEventClick = (infoData: any) => {    
    setTrainingSessionData(setSessionFromEvent(infoData.event));
    setIsOpenPopup(true);

  }
  
  const handleSelect = (arg: any) => {
    setTrainingSessionData(setSessionFromEvent(arg));
    setIsOpenPopup(true);

  }

  const handleSaveOnClick = (SessionData:TrainingSessionData) => {

    console.log(SessionData)

    const eventIndex = events.findIndex((event) => (event.id?.toString()) === (SessionData.id?.toString()));
    const newEvent : EventInput = 
      { 
        id: (SessionData.id !== 0 ? SessionData.id: events.length + 1).toString(),
        IsNew: SessionData.id == 0 || SessionData.IsNew,
        title: SessionData.name, 
        Startdate: SessionData.trainingSessionDate,
        start: `${SessionData.trainingSessionDate}T${SessionData.StartTime}` , 
        end: `${SessionData.trainingSessionDate}T${SessionData.EndTime}`,
        allDay: SessionData.IsAllDay,
        startEventTime: SessionData.StartTime,
        endEventTime: SessionData.EndTime,
        description: SessionData.description,
      };
      console.log("newEvent", newEvent);  
      if(eventIndex !== -1){
        const updatedEvents = [...events];
        updatedEvents[eventIndex] = newEvent;
        setEvents(updatedEvents);
      }
      else
        setEvents([...events, newEvent]);
      console.log("events", events)
      setIsOpenPopup(false);
  }
  
  const handleSaveEvents = () => {
  
    let trainingSessionsList : TrainingSessionData[] = [];  
    events.map((event) => {
      const trainingSessionData : TrainingSessionData = {
        id: (event.IsNew ? 0 : parseInt((event.id ? event.id : '0'), 10)),
        name: event.title || '',
        description: event.description,
        trainingSessionDate: event.Startdate,
        StartTime : event.startEventTime,
        EndTime : event.endEventTime,
        IsAllDay : event.allDay || false,
        trainingId: trainingId,
        IsNew: event.IsNew,
      };
      trainingSessionsList.push(trainingSessionData);
    });
    console.log(trainingSessionsList);
    try{
      SaveTrainingSessionsList(trainingSessionsList);
    }
    catch(error){
      alert(error);
    }
    
  }

  const setSessionFromEvent = (arg: any) => {

    const trainingSessionData : TrainingSessionData = {
      id: (arg.id ? parseInt(arg.id, 10) : 0),
      name: arg.title || '',
      description: (arg.extendedProps? arg.extendedProps.description : ''),
      trainingSessionDate: arg.startStr.split('T')[0],
      StartTime : arg.startStr.split('T')[1].split('+')[0],
      EndTime : arg.endStr.split('T')[1].split('+')[0],
      IsAllDay : arg.allDay || false,
      trainingId: trainingId,
      IsNew: (arg.extendedProps? arg.extendedProps.IsNew : false),
    };
    console.log(trainingSessionData);
    
    return trainingSessionData;
  }
  
    return (
        <>
          <FullCalendar
            plugins={[ dayGridPlugin, interactionPlugin , timeGridPlugin]}
            initialView="dayGridMonth"
            headerToolbar={{
                left: 'back prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay saveEvent',
              }}
            validRange={{
                start: trainingStartDate, // Set the minimum date
              }}
            firstDay={1}
            height={900}
            events={events}
            customButtons={{
              saveEvent: {
                text: "Save Sessions",
                click: function() {
                  handleSaveEvents();
                }
              },
              back: {
                text: "Back",
                click: function() {
                  window.history.back();
                }
              }
            }}
            eventStartEditable={true}
            eventDurationEditable={true}
            displayEventEnd={true}
            editable={true}
            droppable={true}
            slotMinTime={"06:00:00"}
            slotMaxTime={"23:00:00"}
            selectable={true}
            dayMaxEventRows={true}
            select={handleSelect}
            // eventDragStop={(arg) => console.log(arg)}
            eventDrop={(arg) => console.log(arg.event.start)}
            eventClick={handleEventClick}
          />
            {IsOpenPopup && (
              <Popup Header={<></>}
              Content={<TrainingSession trainingSessiosData={trainingSessionData} onSaveClicked={handleSaveOnClick}/>} 
              Actions={<></>} 
              IsOpen={true} OnClose={() => setIsOpenPopup(false)} />
            )}
        </>
    );
};

export default CalendarPlanification;
