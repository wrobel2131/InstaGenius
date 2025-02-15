export interface GraphicOptions {
  model: 'dall-e-2' | 'dall-e-3';
  style?: 'vivid' | 'natural';
  quality?: 'standard' | 'hd';
}

export interface DescriptionOptions {
  model: 'gpt-3' | 'gpt-4';
}

export interface GenerateOptions {
  graphicOptions: GraphicOptions;
  descriptionOptions: DescriptionOptions;
}
