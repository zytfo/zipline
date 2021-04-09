import styled from "styled-components";

export const MainContainer = styled.div`
  display: grid;
  grid-template-columns: minmax(0, 4fr) minmax(0, 12fr);
  align-items: flex-start;
  max-width: 1128px;
  width: 100%;
  column-count: 2;
  column-gap: 24px;
  margin: 0 auto;
`;
