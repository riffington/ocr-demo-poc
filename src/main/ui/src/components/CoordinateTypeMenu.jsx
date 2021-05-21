import React from 'react';

const CoordinateTypeMenu = ({
    pageId,
    currentCoordsType,
    changeCoordsType,
}) => {
    const handleChange = (e) => {
        changeCoordsType(pageId, e.target.value);
        e.preventDefault();
    };

    return (
        <>
            {currentCoordsType ? (
                <div>
                    <select
                        defaultValue={currentCoordsType}
                        onChange={handleChange}
                    >
                        <option value="AREA">Area</option>
                        <option value="PARAGRAPH">Paragraph</option>
                        <option value="LINE">Line</option>
                        <option value="WORD">Word</option>
                    </select>
                </div>
            ) : (
                <div></div>
            )}
        </>
    );
};

export default CoordinateTypeMenu;
