import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Ring } from 'react-spinners-css';
import { ImageMapAttributes, dashboardPage } from '../Constants.js';
import ImageMapper from 'react-image-mapper';

const SpellcheckTask = ({
    contentType,
    isFetchingTask,
    isSavingTask,
    currentTaskData,
    fetchSpellcheckTask,
    saveSpellcheckTaskResult,
    message,
}) => {
    useEffect(() => {
        if (!isFetchingTask && !currentTaskData) {
            fetchSpellcheckTask(contentType);
        }
    }, [currentTaskData]);

    const handleSubmit = (e) => {
        const responseData = {
            id: currentTaskData.id,
            words: currentTaskData.words.map((word) => {
                const wordId = word.id;
                return {
                    id: wordId,
                    correctedText: document.getElementById(wordId).value,
                };
            }),
        };
        saveSpellcheckTaskResult(responseData);
        e.preventDefault();
    };

    const { Width, DefaultStrokeColor } = ImageMapAttributes;
    const imageUrl = 'api/image/' + currentTaskData?.imageRef;

    const trueImageWidth =
        currentTaskData?.pageBoundingBox?.bottomRightX -
        currentTaskData?.pageBoundingBox?.topLeftX;

    const buildMapData = () => {
        // add box for each word
        const areas = currentTaskData.words.map((word) => {
            const boxInfo = word.boundingBox;
            return {
                name: word.id,
                shape: 'rect',
                coords: [
                    boxInfo.topLeftX,
                    boxInfo.topLeftY,
                    boxInfo.bottomRightX,
                    boxInfo.bottomRightY,
                ],
                fillColor: 'rgba(255, 0, 0, 0.3)',
                preFillColor: 'rgba(255, 0, 0, 0.3)',
            };
        });

        // // add box for line
        // const lineBox = currentTaskData.boundingBox;
        // areas.push({
        //     name: 'lineBox',
        //     shape: 'rect',
        //     coords: [
        //         lineBox.topLeftX,
        //         lineBox.topLeftY,
        //         lineBox.bottomRightX,
        //         lineBox.bottomRightY,
        //     ],
        //     // fillColor: 'rgba(255, 255, 0, 0.5)',
        //     preFillColor: 'rgba(255, 255, 0, 0)',
        // });

        return {
            name: 'spellcheck-map',
            areas: areas,
        };
    };

    const listItems = currentTaskData?.words.map((word) => (
        <div key={word.id} className="spacing">
            <span className="title">Word: </span>
            <span>{word.text}</span>
            <div>
                <span className="title">Corrected Text: </span>
                <input type="text" id={word.id} />
            </div>
        </div>
    ));

    return (
        <div className="flex-container">
            <div className="flex-child">
                {isFetchingTask ? (
                    <>
                        <h2>Loading task...</h2>
                        <Ring color="#ff992b"></Ring>
                    </>
                ) : currentTaskData ? (
                    <div className="scrollable_left">
                        <ImageMapper
                            src={imageUrl}
                            map={buildMapData()}
                            width={Width}
                            imgWidth={trueImageWidth}
                            strokeColor={DefaultStrokeColor}
                            // height={Height}
                            // imgHeight={trueImageHeight}
                            //onClick={(area) => handleAreaClick(area)}
                            // onMouseEnter={(area) => enterArea(area)}
                            // onMouseLeave={(area) => leaveArea()}
                            // onMouseMove={(area, _, evt) => this.moveOnArea(area, evt)}
                            // onImageClick={(evt) => handleImageClick(evt)}
                            // onImageMouseMove={(evt) => this.moveOnImage(evt)}
                        />
                    </div>
                ) : (
                    <div></div>
                )}
            </div>
            <div className="flex-child right-view">
                <div>
                    <span>
                        Please correct the transcription for the following words
                    </span>
                </div>
                {listItems}
                <div>
                    <Link to={dashboardPage}>Cancel</Link>

                    {isSavingTask ? (
                        <>
                            <h2>Saving results...</h2>
                            <Ring color="#ff992b"></Ring>
                        </>
                    ) : (
                        <button onClick={handleSubmit} className="left_spacing">
                            Submit
                        </button>
                    )}
                </div>
                <span>{message}</span>
            </div>
        </div>
    );
};

export default SpellcheckTask;
