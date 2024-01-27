import API from "../API";

export interface TrainingSessionData {
    id: number;
    name: string;
    description: string;
    trainingSessionDate: string;
    StartTime : string;
    EndTime : string;
    IsAllDay : boolean;
    trainingId: number;
    trainerId: number;
}