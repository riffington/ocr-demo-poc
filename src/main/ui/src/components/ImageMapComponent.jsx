import React, { useEffect } from 'react';
import { Ring } from 'react-spinners-css';
import { ImageMapAttributes } from '../Constants.js';
import ImageMapper from 'react-image-mapper';
import CoordinateTypeMenu from '../containers/CoordinateTypeMenu.jsx';

const ImageMapComponent = ({
    isFetchingData,
    trainingData,
    selectedData,
    fetchTrainingData,
    selectArea,
}) => {
    const { Width, DefaultStrokeColor } = ImageMapAttributes;
    useEffect(() => {
        if (!isFetchingData && !trainingData) {
            fetchTrainingData(9, 'LINE'); // TODO update to use store
        }
    }, []);

    // const [selectedAreaMap, setSelectedAreaMap] = useState(new Map());

    const handleAreaClick = (area) => {
        selectArea({ key: area.name, value: area.coords });
        // const mapCopy = new Map(selectedAreaMap);
        // if (mapCopy.has(area.name)) {
        //     mapCopy.delete(area.name);
        // } else {
        //     mapCopy.set(area.name, area.coords);
        // }
        // setSelectedAreaMap(mapCopy);
    };

    const trueImageWidth =
        trainingData?.boundingBox?.bottomRightX -
        trainingData?.boundingBox?.topLeftX;

    const imageUrl = 'api/image/' + trainingData?.imageName; // TODO this should be by ID not name

    const buildMapData = () => {
        const areas = trainingData?.boundingBoxCoordinates?.map((boxInfo) => {
            const selectedItem = selectedData.get(boxInfo.boxId);
            if (selectedItem) {
                return {
                    name: boxInfo.boxId,
                    shape: 'rect',
                    coords: boxInfo.coordinates,
                    fillColor: 'rgba(255, 255, 0, 0.5)',
                    preFillColor: 'rgba(255, 255, 0, 0.5)',
                };
            }

            return {
                name: boxInfo.boxId,
                shape: 'rect',
                coords: boxInfo.coordinates,
                fillColor: 'rgba(255, 255, 0, 0.48)',
                preFillColor: 'rgba(255, 255, 0, 0)',
            };
        });

        return {
            name: 'training-map',
            areas: areas,
        };
    };

    return (
        <div className="flex-container">
            <div className="flex-child">
                {isFetchingData ? (
                    <>
                        <h2>Loading training data...</h2>
                        <Ring color="#ff992b"></Ring>
                    </>
                ) : trainingData ? (
                    <ImageMapper
                        src={imageUrl}
                        map={buildMapData()}
                        width={Width}
                        imgWidth={trueImageWidth}
                        strokeColor={DefaultStrokeColor}
                        // height={Height}
                        // imgHeight={trueImageHeight}
                        onClick={(area) => handleAreaClick(area)}
                        // onMouseEnter={(area) => enterArea(area)}
                        // onMouseLeave={(area) => leaveArea()}
                        // onMouseMove={(area, _, evt) => this.moveOnArea(area, evt)}
                        // onImageClick={(evt) => handleImageClick(evt)}
                        // onImageMouseMove={(evt) => this.moveOnImage(evt)}
                    />
                ) : (
                    <div></div>
                )}
            </div>
            <div className="flex-child right-view">
                <CoordinateTypeMenu></CoordinateTypeMenu>
            </div>
        </div>
    );
};

export default ImageMapComponent;
