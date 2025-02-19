import * as React from 'react';
import { Outlet } from 'react-router';
import {DashboardLayout, ThemeSwitcher} from '@toolpad/core/DashboardLayout';
import { PageContainer } from '@toolpad/core/PageContainer';
import {IconButton, Stack, TextField, Tooltip} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import Typography from "@mui/material/Typography";

function ToolbarActionsSearch() {
    return (
        <Stack direction="row">
            <Tooltip title="Search" enterDelay={1000}>
                <div>
                    <IconButton
                        type="button"
                        aria-label="search"
                        sx={{
                            display: {xs: 'inline', md: 'none'},
                        }}
                    >
                        <SearchIcon/>
                    </IconButton>
                </div>
            </Tooltip>
            <TextField
                label="Search"
                variant="outlined"
                size="small"
                slotProps={{
                    input: {
                        endAdornment: (
                            <IconButton type="button" aria-label="search" size="small">
                                <SearchIcon/>
                            </IconButton>
                        ),
                        sx: {pr: 0.5},
                    },
                }}
                sx={{display: {xs: 'none', md: 'inline-block'}, mr: 1}}
            />
            <ThemeSwitcher/>
        </Stack>
    );
}

function SidebarFooter({mini}) {
    return (
        <Typography
            variant="caption"
            sx={{m: 1, whiteSpace: 'nowrap', overflow: 'hidden'}}
        >
            {mini ? '© MUI' : `© ${new Date().getFullYear()} Wedding~Vibes`}
        </Typography>
    );
}

function Layout() {
    return (
        <DashboardLayout
            slots={{
                // appTitle: CustomAppTitle,
                toolbarActions: ToolbarActionsSearch,
                sidebarFooter: SidebarFooter,
            }}
        >
            <PageContainer>
                <Outlet />
            </PageContainer>
        </DashboardLayout>
    );
}
export default Layout;