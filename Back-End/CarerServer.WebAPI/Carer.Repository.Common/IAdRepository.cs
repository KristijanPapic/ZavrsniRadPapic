using Carer.Common;
using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Carer.Repository.Common
{
    public interface IAdRepository
    {
        Task DeleteAdAsync(Guid id);
        Task<AdDomainModel> GetAdByIdAsync(Guid id);
        Task<List<AdDomainModel>> GetAllAdsAsync(AdFilter filter);
        Task PostAdAsync(AdDomainModel ad);
        Task PutAdAsync(AdDomainModel ad);

        Task SetActiveAdAsync(Guid adId);
        void SetStatusAdAsync(Guid adId, int status);

        int GetAdStatusAsync(Guid adId);
    }
}