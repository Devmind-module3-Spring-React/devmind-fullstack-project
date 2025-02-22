import {createSlice} from "@reduxjs/toolkit";

const vendorSlices = createSlice({
    name: "vendors",
    initialState: {
        vendors: []
    },
    reducers: {
        setVendors: (state, action) => {
            state.vendors = action.payload;
        },
        addVendor: (state, action) => {
            //the actual state is not modified, this is a wrapper, state is immutable
            state.vendors.push({
                companyName: action.payload.companyName,
                companyEmail: action.payload.companyEmail,
                location: action.payload.location,
                websiteUrl: action.payload.websiteUrl,
                phoneNumber: action.payload.phoneNumber});
        }
    }
});

export const {setVendors, addVendor} = vendorSlices.actions;
export default vendorSlices.reducer;