import { useState, useEffect } from 'react';
import "./parklist.css";
import ParkListItem from "./ParkListItem";
import axios from 'axios';
import useLocationStore from'../LocationStore';
import useStore from '../Parkinfo/Store';

import axiosInstance from '../../../service/axiosInstance';

interface Location {
    UserLatitude: number;
    UserLongitude: number;
}

interface Parks {
    parkIndex: number;
    parkName: string;
    parkAddress: string;
    parkLatitude: number;
    parkLongitude: number;
    parkArea: number;
}


export default function ParkListBody() {

  const [parks, setParks] = useState<Parks[]>([])

  const { setCloseParks, setUserLocation } = useLocationStore();
  const { parkInfoState } = useStore();

  const fetchData = async (locale: any) => {
    const parksData = await getParks(locale);
    console.log(parksData[1].parkIndex)
    setParks(parksData);
    setCloseParks(parksData);
  };

  const getUserLocation = () => {
    const locale: Location = {UserLatitude : 0, UserLongitude: 0}
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          locale.UserLatitude = position.coords.latitude;
          locale.UserLongitude = position.coords.longitude;
          
          fetchData(locale);
          setUserLocation(locale);
        },
      );
    } else {
      console.error('Geolocation is not supported by this browser.');
    }
    return locale;
  };
  
  const getParks = async (userLocation: Location): Promise<Parks[]> => {
    try {
      const response = await axiosInstance.post('irunyou/park/',{
        latitude : userLocation.UserLatitude,
        longitude : userLocation.UserLongitude
    });
      return response.data.data;
    } catch (error) {
      // console.error(error);
      return [];
    }
  };
  


  useEffect(() => {

    getUserLocation();
  }, []);

  return (
    <div className="park-list-container">
      {parks ? parks.map((park) => (
        <ParkListItem key={park.parkName} park={park} />
      )) : null}
    </div>
  );
  
}